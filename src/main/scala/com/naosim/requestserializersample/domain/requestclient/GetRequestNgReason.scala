package com.naosim.requestserializersample.domain.requestclient

sealed abstract class GetRequestNgReason

object GetRequestNgReason {
  case object NOT_EXIST extends GetRequestNgReason // キュー追加直後にタスクが走った場合に返る => 後で再送
  case object ALREADY_FINISHED extends GetRequestNgReason// 何もしない
  case object DO_LATER extends GetRequestNgReason// 何もしない
}

