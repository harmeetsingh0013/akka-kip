package com.knoldus.day2.router

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.FromConfig
import akka.util.Timeout
import com.knoldus.day2.router.PoolDynamicCounterActor.State
import com.typesafe.config.ConfigFactory
import akka.pattern.ask
import scala.concurrent.duration.DurationInt

/**
  * Created by harmeet on 16/3/17.
  */
class PoolDynamicCounterActor extends Actor {
  var counter = 0;

  override def receive = {
    case msg: String =>
      counter += 1
      Thread.sleep(1000)
      println(s"Counter incremented by ${self.path}")

    case State => sender() ! counter
  }
}

object PoolDynamicCounterActor extends App {
  case object State

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /poolRouter {
      |   router = round-robin-pool
      |   resizer {
      |      pressure-threshold = 0
      |      lower-bound = 2
      |      upper-bound = 15
      |      messages-per-resize = 1
      |    }
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)
  val router = system.actorOf(FromConfig.props(Props[PoolDynamicCounterActor]), "poolRouter")

  for(i <- 1 to 20 ){
    router ! "increment by one"
  }

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  Thread.sleep(1000)

  val f = router ? State
  f.foreach(println _)
}
