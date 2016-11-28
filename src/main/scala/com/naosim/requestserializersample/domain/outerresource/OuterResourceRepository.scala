package com.naosim.requestserializersample.domain.outerresource

trait OuterResourceRepository {
  def execute(request: Request): Response
}
