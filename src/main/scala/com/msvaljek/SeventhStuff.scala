package com.msvaljek

object SeventhStuff extends App {
  import scala.io.Source
  import java.net.URL

  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Request URL is blocked for the good of people")
    else
      Right(Source.fromURL(url))
}
