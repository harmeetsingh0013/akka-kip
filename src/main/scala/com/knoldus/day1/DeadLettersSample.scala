package com.knoldus.day1

import akka.actor.{Actor, ActorSystem, DeadLetter, Props}

/**
  * Created by harmeet on 15/3/17.
  */

class DeadLetterMonitor extends Actor {

  override def receive = {
    case d: DeadLetter =>
      println(d)
  }
}

object DeadLettersSample extends App {

  val system = ActorSystem("DeadLettersSample")
  val ref = system.actorSelection("akka://DeadLettersSample/user/deadletter")
  ref ! "Message goes to deadletters"

  val listener = system.actorOf(Props(classOf[DeadLetterMonitor]))
  system.eventStream.subscribe(listener, classOf[DeadLetter])
}
