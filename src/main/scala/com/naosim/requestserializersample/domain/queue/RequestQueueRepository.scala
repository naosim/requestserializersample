package com.naosim.requestserializersample.domain.queue

import com.naosim.requestserializersample.domain.RequestId

trait RequestQueueRepository {
  def enqueue(requestId: RequestId)
  def dequeue(): Option[RequestId]
  def remove(requestId: RequestId)
}
