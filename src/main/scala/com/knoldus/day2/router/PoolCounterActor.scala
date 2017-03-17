package com.knoldus.day2.router

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.routing.{BalancingPool, FromConfig, RoundRobinPool}
import akka.util.Timeout
import com.knoldus.day2.router.PoolCounterActor.State
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.DurationInt

/**
  * Created by harmeet on 16/3/17.
  */

//The example for using router simple way
class PoolCounterActor extends Actor {

  var counter = 0;

  override def receive = {
    case msg: String =>
      counter += 1
      println(s"Counter incremented by ${self.path}")

    case State => sender() ! counter
  }
}

object PoolCounterActor extends App {
  case object State

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /poolRouter {
      |   router = round-robin-pool
      |   nr-of-instances = 5
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)
  val router = system.actorOf(FromConfig.props(Props[PoolCounterActor]), "poolRouter")

//  val system = ActorSystem("RouterSystem")
//  val router = system.actorOf(RoundRobinPool(5).props(Props[PoolCounterActor]), "poolRouter")

  router ! "increment by one"
  router ! "increment by one"
  router ! "increment by one"
  router ! "increment by one"

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  Thread.sleep(1000)

  val f = router ? State
  f.foreach(println _)

  val f1 = router ? State
  f1.foreach(println _)
}
