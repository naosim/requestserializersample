package com.naosim.requestserializersample.datasource.outerresource

import com.naosim.requestserializersample.domain.outerresource._
import org.springframework.stereotype.Component

@Component
class OuterResourceRepositoryWeb extends OuterResourceRepository{
  override def execute(request: Request): Response = {
//    return Response(ResponseStatusCode(500), ResponseText("<result>ng</result>"))// ダミー
    return Response(ResponseStatusCode(200), ResponseText("<result>ok</result>"))// ダミー
  }
}
