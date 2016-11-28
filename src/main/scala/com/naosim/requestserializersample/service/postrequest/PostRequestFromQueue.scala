package com.naosim.requestserializersample.service.postrequest

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.domain.outerresource.{OuterResourceRepository, Request}
import com.naosim.requestserializersample.domain.queue.RequestQueueRepository
import com.naosim.requestserializersample.domain.requestclient.GetRequestNgReason.{ALREADY_FINISHED, DO_LATER, NOT_EXIST}
import com.naosim.requestserializersample.domain.requestclient._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PostRequestFromQueue @Autowired()(
                                         requestQueueRepository: RequestQueueRepository,
                                         requestClientRepository: RequestClientRepository,
                                         outerResourceRepository: OuterResourceRepository
                                       ) {


  @Scheduled(fixedRate = 1000, initialDelay = 5000)
  def loop(): Unit = {
    run()
  }

  // 1件だけ処理する
  def run(): Unit = {
    val requestIdOption = requestQueueRepository.dequeue()
    if(requestIdOption.isEmpty) {
      return // キューが空なので何もしない
    }

    val requestId = requestIdOption.get
    val request: Either[GetRequestNgReason, Request] = requestClientRepository.getRequest(requestId)

    request match {
      case Left(ngReason) => onError(requestId, ngReason)
      case Right(request) => onExecute(requestId, request)
    }
  }

  // 送信
  def onExecute(requestId: RequestId, request: Request): Unit = {
    val response = outerResourceRepository.execute(request)
    requestClientRepository.sendResponse(requestId, response)
    requestQueueRepository.remove(requestId)
  }

  def onError(requestId: RequestId, getRequestNgReason: GetRequestNgReason): Unit = {
    println("error: " + getRequestNgReason.getClass.getSimpleName)
    getRequestNgReason match {
      case ALREADY_FINISHED => requestQueueRepository.remove(requestId)
      case NOT_EXIST        => requestQueueRepository.enqueue(requestId)
      case DO_LATER         => requestQueueRepository.enqueue(requestId)
    }
  }

}
