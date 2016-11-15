package com.naosim.requestserializersample.domain.outerresource

case class Response(val responseStatusCode: ResponseStatusCode, val responseText: ResponseText)
case class ResponseStatusCode(val value: Int)
case class ResponseText(val value: String)