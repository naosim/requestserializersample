package com.naosim.requestserializersample.api

import java.net.URI
import java.time.LocalDate

import com.naosim.requestserializersample.api.request.SendResponseRequest
import com.naosim.requestserializersample.datasource.requestclient.RequestClientRepositoryWeb
import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.service.postrequest.PostRequestFromQueue
import com.naosim.requestserializersample.service.queue.{DequeueRequestService, EnqueueRequestService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http._
import org.springframework.web.bind.annotation._
import org.springframework.web.client.RestTemplate

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * クライアントサイドの感じ
  * @param enqueueRequestService
  * @param dequeueRequestService
  * @param postRequestFromQueue
  */
@RestController
class DemoController @Autowired()(
                                     val enqueueRequestService: EnqueueRequestService,
                                     val dequeueRequestService: DequeueRequestService,
                                     val postRequestFromQueue: PostRequestFromQueue
                                   ) {

  @RequestMapping(Array("/request"))
  def request(): String = {
    val requestId = RequestId(System.currentTimeMillis().toString)

    DemoController.table += requestId -> "waiting"// 状態保存

    val rest = new RestTemplate()
    val headers = new HttpHeaders()
    headers.add("Content-Type", "application/json")
    headers.add("Accept", "*/*")
    val requestEntity:RequestEntity[String] = new RequestEntity[String](HttpMethod.GET, new URI(s"http://localhost:8080/enqueue?requestIdForm=" + requestId.value))
    val responseEntity: ResponseEntity[java.util.Map[String, Object]] = rest.exchange(requestEntity, classOf[java.util.Map[String, Object]]);

    println("request " + requestId.value)
    return "ok"
  }

  @RequestMapping(Array("/findRequest"))
  def findRequest(@RequestParam("requestIdForm") requestIdForm: String): java.util.Map[String, String] = {
    println("findRequest " + requestIdForm)

    return if(!DemoController.isLater) {
      DemoController.table += RequestId(requestIdForm) -> "processing"// 状態保存
      val map = mutable.Map("result" -> "ok")
      map += "result" -> "ok"
      map += "url" -> "http://www.biglobe.co.jp/"
      map += "method" -> "get"
      map.asJava
    } else {
      val map = mutable.Map("result" -> "ng")
      map += "ngReason" -> "do_later"
      map.asJava
    }

  }


  @RequestMapping(value = Array("/sendResponse"), method = Array(RequestMethod.POST))
  def send(@RequestBody sendResponseRequest: SendResponseRequest): java.util.Map[String, String] = {
    println("sendResponse " + sendResponseRequest.requestId + ", " + sendResponseRequest.statusCode + ": " + sendResponseRequest.responseBody)

    DemoController.table += RequestId(sendResponseRequest.requestId) -> "finished"// 状態保存

    // statusCodeが200なら消す
    // statusCodeが200以外なら再なら消す

    return Map("result" -> "ok").asJava
  }




  @RequestMapping(Array("/swich"))
  def swichLater(): String = {
    DemoController.isLater = !DemoController.isLater
    return "ok"
  }

  @RequestMapping(Array("/table"))
  def table(): java.util.List[java.util.List[String]] = {
    return DemoController.table.keys.map(v => List(v.value, DemoController.table.get(v).get).asJava).toList.asJava
  }
}

object DemoController {
  var isLater = false

  var table = mutable.Map.empty[RequestId, String]// requestId, status
}
