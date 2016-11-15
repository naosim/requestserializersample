package com.naosim.requestserializersample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

object App {
  def main(args: Array[String]): Unit =
    SpringApplication.run(classOf[App], args: _*)
}


@SpringBootApplication
class App