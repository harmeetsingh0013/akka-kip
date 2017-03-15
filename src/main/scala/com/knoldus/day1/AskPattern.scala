package com.knoldus.day1

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration.DurationInt

/**
  * Created by harmeet on 15/3/17.
  */

class TeaSeller extends Actor {

  override def receive = {
    case Tea =>
      sender() ! "Superb Tea ... "
    case Smosa =>
      sender() ! "Spicy Smosa with Chatni ... "
    case msg =>
      sender() ! "Sorry not available .. "
  }
}

case object Tea
case object Smosa
case object Mathi

object TellPattern extends App {

  val system = ActorSystem("TellPattern")
  val props = Props[TeaSeller];
  val ref = system.actorOf(props)

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val fTea = ref ? Tea
  println("Order Tea ... ")
  fTea.foreach(println _)

  val fSmosa = ref ? Smosa
  println("Order Smosa ... ")
  fSmosa.foreach(println _)

  val fMathi = ref ? Mathi
  println("Order Mathi ... ")
  fMathi.foreach(println _)
}
