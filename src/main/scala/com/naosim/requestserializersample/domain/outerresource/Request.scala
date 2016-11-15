package com.naosim.requestserializersample.domain.outerresource

case class Request(
                  val requestUrl: RequestUrl,
                  val requestMethod: RequestMethod,
                  val requestHeader: RequestHeader,
                  val requestBody: RequestBody
)

case class RequestUrl(val value: String)
case class RequestHeader(val value: String)
case class RequestBody(val value: String)

sealed trait RequestMethod {
  val value: String
}
case object method_get extends RequestMethod {
  override val value: String = "get"
}
case object method_post extends RequestMethod {
  override val value: String = "post"
}

