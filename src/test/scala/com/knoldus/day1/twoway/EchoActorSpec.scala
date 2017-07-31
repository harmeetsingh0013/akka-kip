package com.knoldus.day1.twoway

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

/**
  * Created by harmeet on 16/3/17.
  */
class EchoActorSpec extends TestKit(ActorSystem("test-system")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers with ImplicitSender {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "Echo Actor " must {
    "Reply with the same message it receives without ask" in {
      val ref = system.actorOf(EchoActor.props)
      ref ! "some message"
      expectMsg("some message")
    }
  }
}
