package com.knoldus.day2.oneway

import akka.actor.{ActorSystem, UnhandledMessage}
import akka.testkit.{CallingThreadDispatcher, EventFilter, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

/**
  * Created by harmeet on 16/3/17.
  */

object SideEffectingActorSpec {
  val testSystem = {
    val config = ConfigFactory.parseString(
      """
        |akka.loggers = [akka.testkit.TestEventListener]
      """.stripMargin
    )
    ActorSystem("test-system", config)
  }
}

import SideEffectingActorSpec._

class SideEffectingActorSpec extends TestKit(testSystem) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "The Side Effecting Actor" must {
    "say Hello World when receive a 'World' string " in {
      val dispatcherId = CallingThreadDispatcher.Id
      val props = SideEffectingActor.props.withDispatcher(dispatcherId)
      val ref = system.actorOf(props)

      EventFilter.info(message = "Hello World", occurrences = 1)
          .intercept{
            ref ! "World"
          }

    }

    "say something else and see what happens" in {
      val props = SideEffectingActor.props
      val ref = system.actorOf(props)

      system.eventStream.subscribe(testActor, classOf[UnhandledMessage])
      ref ! 2
      expectMsg(UnhandledMessage(2, system.deadLetters, ref))
    }
  }
}

