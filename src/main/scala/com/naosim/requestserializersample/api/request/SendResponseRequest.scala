package com.naosim.requestserializersample.api.request

class SendResponseRequest {
  var requestId: String = null;
  var responseBody: String = null;
  var statusCode: Int = 0;

  def setRequestId(v:String): Unit = {
    this.requestId = v
  }

  def setResponseBody(v:String): Unit = {
    this.responseBody = v
  }

  def setStatusCode(v: Int): Unit = {
    this.statusCode = v
  }
}
