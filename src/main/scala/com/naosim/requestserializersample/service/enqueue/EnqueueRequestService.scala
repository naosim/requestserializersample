package com.naosim.requestserializersample.service.enqueue

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.domain.queue.RequestQueueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.{Component, Service}

@Service
class EnqueueRequestService @Autowired() (requestQueueRepository: RequestQueueRepository) {
  def enqueue(requestId: RequestId): Unit = requestQueueRepository.enqueue(requestId)
}
