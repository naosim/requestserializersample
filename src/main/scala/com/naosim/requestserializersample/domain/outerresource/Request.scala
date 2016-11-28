package com.naosim.requestserializersample.domain.outerresource

case class Request(
                  val requestUrl: RequestUrl,
                  val requestMethod: RequestMethod,
                  val requestHeader: Option[RequestHeader] = None,
                  val requestBody: Option[RequestBody] = None
)

case class RequestUrl(val value: String)
case class RequestHeader(val value: String)
case class RequestBody(val value: String)

sealed class RequestMethod(val value: String)

object RequestMethod {
  case object get extends RequestMethod("get")
  case object post extends RequestMethod("post")

  def create(text: String): RequestMethod = List(get, post).find(v => v.value == text).get
}



