package com.naosim.requestserializersample.datasource.requestclient

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.domain.outerresource._
import com.naosim.requestserializersample.domain.requestclient.{GetRequestNgReason, RequestClientRepository}
import org.springframework.stereotype.Component

/**
  * リクエスト呼び出し元への要求
  */
@Component
class RequestClientRepositoryWeb extends RequestClientRepository {
  override def getRequest(requestId: RequestId): Either[GetRequestNgReason, Request] = {
    Right(Request(
      RequestUrl("http://hgogehogefoo.com"),
      method_get,
      RequestHeader(""),
      RequestBody("body")
    ))
  }

  override def sendResponse(response: Response): Unit = {

  }
}
