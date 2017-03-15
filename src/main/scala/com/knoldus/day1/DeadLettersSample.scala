package com.knoldus.day1

import akka.actor.{Actor, ActorSystem, DeadLetter, PoisonPill, Props}

/**
  * Created by Harmeet Singh(Taara) on 15/3/17.
  */

class DeadLetterMonitor extends Actor {

  override def receive = {
    case d: DeadLetter => println(d)
  }
}

class SuicideActor extends Actor {
  override def receive = {
    case _ =>
  }
}

object DeadLettersSample extends App {

  val system = ActorSystem("DeadLettersSample")
  val ref = system.actorOf(Props[SuicideActor])
  ref ! PoisonPill
  ref ! "Message goes to deadletters"

  val listener = system.actorOf(Props(classOf[DeadLetterMonitor]))
  system.eventStream.subscribe(listener, classOf[DeadLetter])
}
