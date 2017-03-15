package com.knoldus.day1

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration.DurationInt

/**
  * Created by harmeet on 15/3/17.
  */

case object PurchaseCar
case object BrandNewCar

class CarBroker extends Actor {

  override def receive = {
    case PurchaseCar =>
      println("I am taking my commission and forward request to agency")
      context.actorOf(Props[CarAgency]).forward(PurchaseCar)

    case msg => println("Sorry !! I am only Car Broker .. ")
  }
}

class CarAgency extends Actor {

  override def receive = {
    case PurchaseCar =>
      println("Sure, we will send brand new car with home delivery facility .. ")
      sender() ! BrandNewCar

    case msg => println("We are only selling cars ... ")
  }
}

object ForwardPattern extends App {

  val system = ActorSystem("ForwardPattern")
  val props = Props[CarBroker];
  val ref = system.actorOf(props)

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  println("Purchasing a brand new car ... ")
  val fCar = ref ? PurchaseCar
  fCar.foreach(println _)
}