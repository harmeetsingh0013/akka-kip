package com.knoldus.day1

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by Harmeet Singh(Taara) on 15/3/17.
  */
class EchoActor extends Actor {

  var counter = 0;

  override def receive = {
    case msg =>
      counter += 1
      println(s"Hello Folks I am receiving a message '${msg}'")
  }
}

object EchoActor extends App {

  val system = ActorSystem("EchoActor")
  val props = Props[EchoActor];
  val ref = system.actorOf(props)
  ref ! "Welcome to Knoldus .. :D "
  println(ref.path)

  val selection = system.actorSelection(ref.path)
  selection ! "Hey team Actor Selection working ... "
}
