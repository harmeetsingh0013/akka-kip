package com.knoldus.day1.oneway

import akka.actor.{Actor, ActorLogging, Props}

/**
  * Created by harmeet on 16/3/17.
  */
class SideEffectingActor extends Actor with ActorLogging {

  override def receive = {
    case s: String => log.info("Hello {}", s)
  }
}

object SideEffectingActor {
  def props = Props[SideEffectingActor]
}

