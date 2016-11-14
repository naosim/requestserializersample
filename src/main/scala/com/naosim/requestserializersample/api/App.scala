package com.naosim.requestserializersample.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

object App {
  def main(args: Array[String]): Unit =
    SpringApplication.run(classOf[App], args: _*)
}


@SpringBootApplication
class App

@RestController
class HelloController {
  @RequestMapping(Array("/message"))
  def message: String =
    "Hello World!!"
  // "こんにちは、世界！！"
}