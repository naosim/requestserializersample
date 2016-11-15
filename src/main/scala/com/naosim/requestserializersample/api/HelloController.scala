package com.naosim.requestserializersample.api

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.service.postrequest.PostRequestFromQueue
import com.naosim.requestserializersample.service.queue.{DequeueRequestService, EnqueueRequestService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMapping, RequestParam, RestController}

import collection.JavaConversions._

@RestController
class HelloController @Autowired() (
                                     val enqueueRequestService: EnqueueRequestService,
                                     val dequeueRequestService: DequeueRequestService,
                                     val postRequestFromQueue: PostRequestFromQueue
                                   ) {

  @RequestMapping(Array("/enqueue"))
  def euqueue(@RequestParam("requestIdForm") requestIdForm: String): java.util.Map[String, String] = {
    enqueueRequestService.enqueue(RequestId(requestIdForm))
    Map("result" -> "ok")
  }

  @RequestMapping(Array("/dequeue"))
  def dequeue: String = dequeueRequestService.dequeue().map(f => f.value).orNull

  @RequestMapping(Array("/loop"))
  def loop: String = {
    postRequestFromQueue.loop()
    return "ok"
  }
}
