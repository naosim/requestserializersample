package com.naosim.requestserializersample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

object App {
  def main(args: Array[String]): Unit =
    SpringApplication.run(classOf[App], args: _*)
}


@SpringBootApplication
@EnableScheduling // スケジュール実行を有効化
class App