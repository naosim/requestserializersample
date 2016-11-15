package com.naosim.requestserializersample.service.queue

import com.naosim.requestserializersample.domain.RequestId
import com.naosim.requestserializersample.domain.queue.RequestQueueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DequeueRequestService @Autowired()(requestQueueRepository: RequestQueueRepository) {
  def dequeue(): Option[RequestId] = requestQueueRepository.dequeue()
}
