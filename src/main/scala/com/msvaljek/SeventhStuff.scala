package com.msvaljek

object SeventhStuff extends App {
  import scala.io.Source
  import java.net.URL

  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Request URL is blocked for the good of people")
    else
      Right(Source.fromURL(url))

  getContent(new URL("http://msvaljek.blogspot.com")) match {
    case Left(msg) => println(msg)
    case Right(source) => source.getLines.foreach(println)
  }

  val content: Either[String, Iterator[String]] =
    getContent(new URL("http://msvaljek.blogspot.com")).right.map(_.getLines)

  val moreContent: Either[String, Iterator[String]] =
    getContent(new URL("http://google.com")).right.map(_.getLines)

  val contentL: Either[Iterator[String], Source] =
    getContent(new URL("http://msvaljek.blogspo")).left.map(Iterator(_))

  val moreContentR:Either[Iterator[String], Source] =
    getContent(new URL("http://google.com")).left.map(Iterator(_))

  val part5 = new URL("http://google.hr/5")
  val part6 = new URL("http://google.hr/6")

  val content2 = getContent(part5).right.map{a => getContent(part6).right.map { b =>
    (a.getLines().size + b.getLines().size) / 2
  }}

  
}
