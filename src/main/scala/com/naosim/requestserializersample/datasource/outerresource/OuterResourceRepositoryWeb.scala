package com.naosim.requestserializersample.datasource.outerresource

import com.naosim.requestserializersample.domain.outerresource._

class OuterResourceRepositoryWeb extends OuterResourceRepository{
  override def execute(request: Request): Response = {
    return Response(ResponseStatusCode(200), ResponseText("{\"result\":\"ok\"}"))// ダミー
  }
}
