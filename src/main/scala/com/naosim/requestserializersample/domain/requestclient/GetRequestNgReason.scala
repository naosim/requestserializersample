package com.naosim.requestserializersample.domain.requestclient

sealed abstract class GetRequestNgReason(val value: String)

object GetRequestNgReason {
  case object NOT_EXIST extends GetRequestNgReason("not_exist") // キュー追加直後にタスクが走った場合に返る => 後で再送
  case object ALREADY_FINISHED extends GetRequestNgReason("already_finished")// 何もしない
  case object DO_LATER extends GetRequestNgReason("do_later")// 何もしない
  case object SERVER_ERROR extends GetRequestNgReason("server_error")// 何もしない

  def create(text: String): GetRequestNgReason = List(NOT_EXIST, ALREADY_FINISHED, DO_LATER, SERVER_ERROR).find(v => v.value == text).get
}

