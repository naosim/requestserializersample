package com.naosim.requestserializersample.datasource.queue

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.domain.queue.RequestQueueRepository
import org.springframework.stereotype.Component

import scala.collection.mutable

/**
  * とりあえずオンメモリでqueueを管理する
  */
@Component
class RequestQueueRepositorySQS extends RequestQueueRepository{
  val queue = new mutable.Queue[RequestId]
  override def enqueue(requestId: RequestId): Unit = queue.enqueue(requestId)
  override def dequeue(): Option[RequestId] = {
    if(queue.size > 0) {
      Some(queue.dequeue())
    } else {
      None
    }
  }

  override def remove(requestId: RequestId): Unit = {
    queue.dequeueFirst(r => r == requestId)
  }

  override def count() = queue.size
}
