package com.msvaljek

object NinthStuff extends App {
  import concurrent.Future
  import concurrent.ExecutionContext.Implicits.global

  val f: Future[String] = Future {"Hello world"}
  // this is scala.concurrent.impl.Promises$Default

  // I guess I know tis stuff good enough ... giving up :)
  // have better things to do :p
}
