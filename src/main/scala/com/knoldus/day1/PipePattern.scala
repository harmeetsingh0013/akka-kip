package com.knoldus.day1

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.pipe

import scala.concurrent.Future

/**
  * Created by Harmeet Singh(Taara) on 15/3/17.
  */

class DBHandler(ref: ActorRef) extends Actor {

  override def receive = {
    case msg =>
      import scala.concurrent.ExecutionContext.Implicits.global
      val future =  Future { "some user fetch from db" }
      future pipeTo ref
  }
}

class EchoUsers extends Actor {

  override def receive = {
    case s: String =>
      println(s"user from db: $s")
  }
}

object PipePattern extends App {

  val system = ActorSystem("PipePatternSystem")
  val echoRef = system.actorOf(Props[EchoUsers])
  val dbRef = system.actorOf(Props(classOf[DBHandler], echoRef))

  dbRef ! "fetch user"
}
