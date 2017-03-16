package com.knoldus.day2.oneway

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

/**
  * Created by harmeet on 16/3/17.
  */
class SendingActorSpec extends TestKit(ActorSystem("test-system")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "A Sending Actor " must {
    "send a message to another actor when it finished processing" in {
      val props = SendingActor.props(testActor)
      val ref = system.actorOf(props, "SendingActor")

      ref ! "increase counter"

      expectMsgPF() {
        case counter: Int =>
          counter must be (1)
      }
    }
  }
}
