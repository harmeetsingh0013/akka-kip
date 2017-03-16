package com.knoldus.day2.oneway

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import com.knoldus.day2.oneway.SilentActor.GetState
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}


/**
  * Created by harmeet on 16/3/17.
  */
class SilentActorSpec extends TestKit(ActorSystem("test-system")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "A silent actor " must {
    "change state when it receives a message, Single Thread Model" in {
      val silentActor = TestActorRef[SilentActor]
      silentActor ! "some message"
      silentActor.underlyingActor.counter === (1)
    }
  }

  "A Silent Actor " must {
    "change state when it receives a messages, Multi Thread Model " in {

      val ref = system.actorOf(Props[SilentActor])
      ref ! "increase counter"
      ref ! "increase counter"
      ref ! "increase counter"
      ref ! "increase counter"

      ref ! GetState(testActor)
      expectMsg(4)
    }
  }
}
