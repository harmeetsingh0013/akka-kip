package com.knoldus.day2.oneway

import akka.actor.{Actor, ActorRef, Props}

/**
  * Created by harmeet on 16/3/17.
  */
class SendingActor(ref: ActorRef) extends Actor {

  var counter = 0;
  override def receive = {
    case s: String =>
      counter += 1
      ref ! counter
  }
}

object SendingActor {
  def props(ref: ActorRef) =
    Props(classOf[SendingActor], ref)
}
