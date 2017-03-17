package com.knoldus.day2.router

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.FromConfig
import akka.util.Timeout
import com.knoldus.day2.router.GroupCounterActor.State
import com.typesafe.config.ConfigFactory
import akka.pattern.ask
import scala.concurrent.duration.DurationInt

/**
  * Created by harmeet on 16/3/17.
  */
class GroupCounterActor extends Actor {

  var counter = 0;

  override def receive = {
    case msg: String =>
      counter += 1
      println(s"Counter incremented by ${self.path}")

    case State => sender() ! counter
  }
}

object GroupCounterActor extends App {
  case object State

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /groupRouter {
      |   router = round-robin-group
      |
      |   routees.paths = [
      |     /user/counter1,
      |     /user/counter2
      |   ]
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)

  system.actorOf(Props[GroupCounterActor], "counter1")
  system.actorOf(Props[GroupCounterActor], "counter2")

  val router = system.actorOf(FromConfig.props(Props[GroupCounterActor]), "groupRouter")

  for(i <- 1 to 4){
    router ! "increment by one"
  }

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  Thread.sleep(1000)

  val f = router ? State
  f.foreach(println _)
}
