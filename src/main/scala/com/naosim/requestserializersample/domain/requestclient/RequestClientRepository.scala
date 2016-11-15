package com.naosim.requestserializersample.domain.requestclient

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.domain.outerresource.{Request, Response}

trait RequestClientRepository {
  def getRequest(requestId: RequestId):Either[GetRequestNgReason, Request]
  def sendResponse(response: Response)
}
