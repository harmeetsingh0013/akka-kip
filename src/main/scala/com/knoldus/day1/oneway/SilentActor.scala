package com.knoldus.day1.oneway

import akka.actor.{Actor, ActorRef}
import com.knoldus.day1.oneway.SilentActor.GetState

/**
  * Created by harmeet on 16/3/17.
  */
class SilentActor extends Actor {

  var counter = 0;

  override def receive = {
    case s: String =>
      counter += 1

    case GetState(ref) => ref ! counter
  }
}

object SilentActor {
  case class GetState(ref: ActorRef)
}
