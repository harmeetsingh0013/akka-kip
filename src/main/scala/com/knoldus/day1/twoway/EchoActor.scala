package com.knoldus.day1.twoway

import akka.actor.{Actor, Props}

/**
  * Created by harmeet on 16/3/17.
  */
class EchoActor extends Actor {

  override def receive = {
    case msg => sender() ! msg
  }
}

object EchoActor {
  def props = Props[EchoActor]
}