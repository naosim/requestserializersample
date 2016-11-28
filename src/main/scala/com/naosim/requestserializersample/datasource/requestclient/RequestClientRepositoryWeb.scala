package com.naosim.requestserializersample.datasource.requestclient

import java.net.URI

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.domain.outerresource._
import com.naosim.requestserializersample.domain.requestclient.{GetRequestNgReason, RequestClientRepository}
import org.springframework.http._
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
  * リクエスト呼び出し元への要求
  */
@Component
class RequestClientRepositoryWeb extends RequestClientRepository {
  override def getRequest(requestId: RequestId): Either[GetRequestNgReason, Request] = {
    return RequestClientRepositoryWeb.getMap(requestId) match {
      case Some(map) => createRequest(map)
      case None => Left(GetRequestNgReason.SERVER_ERROR)
    }
  }

  def createRequest(map: java.util.Map[String, Object]): Either[GetRequestNgReason, Request] = {
    if(map.get("result").toString.toLowerCase != "ok") {
      return Left(GetRequestNgReason.create(map.get("ngReason").toString))// TODO ちゃんと取る
    }

    return Right(Request(
      RequestUrl(map.get("url").toString),
      RequestMethod.create(map.get("method").toString)
    ))

  }

  override def sendResponse(requestId: RequestId, response: Response): Unit = {
    val rest = new RestTemplate()
    val headers = new HttpHeaders()
    headers.add("Content-Type", "application/json")
    headers.add("Accept", "*/*")
    val json = "{\"requestId\": \"" + requestId.value + "\", \"statusCode\":" + response.responseStatusCode.value + ",\"responseBody\": \"" + response.responseText.value + "\"}"
    val requestEntity = new RequestEntity[String](json, headers, HttpMethod.POST, new URI("http://localhost:8080/sendResponse"))
    val responseEntity = rest.exchange(requestEntity, classOf[String])
  }
}

object RequestClientRepositoryWeb {
  def getMap(requestId: RequestId): Option[java.util.Map[String, Object]] = {
    val rest = new RestTemplate()
    val headers = new HttpHeaders()
    headers.add("Content-Type", "application/json")
    headers.add("Accept", "*/*")

    val requestEntity:HttpEntity[String] = new HttpEntity[String](headers)
    val responseEntity: ResponseEntity[java.util.Map[String, Object]] = rest.exchange("http://localhost:8080/findRequest?requestIdForm=" + requestId.value, HttpMethod.GET, requestEntity, classOf[java.util.Map[String, Object]])
    if(!responseEntity.getStatusCode().is2xxSuccessful() && !responseEntity.getStatusCode().is3xxRedirection()) {
      return None
    }
    return Some(responseEntity.getBody())
  }


}
