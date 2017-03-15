package com.knoldus.day1

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration.DurationInt

/**
  * Created by harmeet on 15/3/17.
  */
class AddressFinder extends Actor {

  override def receive = {
    case msg =>
      println("Sender Address: "+sender().path)
      sender() ! sender().path.toString
  }
}

object TempActorSample extends App {

  val system = ActorSystem("TempActorSample")
  val ref = system.actorOf(Props[AddressFinder])

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val f = ref ? "Give me address"

  f foreach println _
}
