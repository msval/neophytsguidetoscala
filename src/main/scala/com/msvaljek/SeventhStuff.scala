package com.msvaljek

object SeventhStuff extends App {
  import scala.io.Source
  import java.net.URL

  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("hevaystuff"))
      Left("Request URL is blocked for the good of people")
    else {
      println(url)
      Right(Source.fromURL(url))
    }

  getContent(new URL("http://msvaljek.blogspot.com")) match {
    case Left(msg) => println(msg)
    case Right(source) => source.getLines.foreach(println)
  }

  val content: Either[String, Iterator[String]] =
    getContent(new URL("http://msvaljek.blogspot.com")).right.map(_.getLines)

  val moreContent: Either[String, Iterator[String]] =
    getContent(new URL("http://google.com")).right.map(_.getLines)

  val contentL: Either[Iterator[String], Source] =
    getContent(new URL("http://msvaljek.blogspot.com")).left.map(Iterator(_))

  val moreContentR:Either[Iterator[String], Source] =
    getContent(new URL("http://google.com")).left.map(Iterator(_))

  val part5 = new URL("https://www.google.hr")
  val part6 = new URL("https://www.google.hr")

/*  val content2: Either[String, Either[String, Int]] = getContent(part5).right.map{ a =>
    getContent(part6).right.map { b =>
    (a.getLines().size + b.getLines().size) / 2
  }}*/

/*  val content3: Either[String, Int] = getContent(part5).right.flatMap(a =>
    getContent(part6).right.map(b =>
      (a.getLines().size + b.getLines().size) / 2))*/

  def averageLineCountWontCompile(url1: URL, url2: URL): Either[String, Int] =
    for {
      source1 <- getContent(url1).right
      source2 <- getContent(url2).right
      lines1 = source1.getLines().size
      lines2 = source2.getLines().size
    } yield (lines1 + lines2) / 2

  //println(averageLineCountWontCompile(new URL("http://google.com"), new URL("http://index.hr")))

  def averageLineCountDesugaredWontCompile(url1: URL, url2: URL): Either[String, Int] =
    getContent(url1).right.flatMap { source1 =>
      getContent(url2).right.map{ source2 =>
        val lines1 = source1.getLines().size
        val line2 = source2.getLines().size
        (lines1, line2)
      }.map{ case (x, y) => x + y / 2 }
    }

  //print(averageLineCountDesugaredWontCompile(new URL("http:/google.com"), new URL("http://index.hr")))

/*  def averageLineCount(url1: URL, url2: URL): Either[String, Int] =
    for {
      source1 <- getContent(url1).right
      source2 <- getContent(url2).right
    _ = println(url1)
      lines1 <- Right(source1.getLines().size).right
      lines2 <- Right(source2.getLines().size).right
    } yield (lines1 + lines2) / 2

  println(averageLineCount(new URL("http://google.com"), new URL("http://google.com")))*/

  // val content: Iterator[String] =
  //  getContent(new URL("http://danielwestheide.com")).fold(Iterator(_), _.getLines())
  //val moreContent: Iterator[String] =
  //  getContent(new URL("http://google.com")).fold(Iterator(_), _.getLines())

  import scala.util.control.Exception.catching

  def handling[Ex <: Throwable, T](exType: Class[Ex])(block: => T): Either[Ex, T] =
    catching(exType).either(block).asInstanceOf[Either[Ex, T]]

  import java.net.MalformedURLException
  def parseUrlA(url: String): Either[MalformedURLException, URL] =
    handling(classOf[MalformedURLException])(new URL(url))

  type Citizen = String
  case class BlackListedResource(url: URL, visitors: Set[Citizen])

  val blacklist = List(
    BlackListedResource(new URL("https://google.com"), Set("John Doe", "Johanna Doe")),
    BlackListedResource(new URL("http://yahoo.com"), Set.empty),
    BlackListedResource(new URL("https://maps.google.com"), Set("John Doe")),
    BlackListedResource(new URL("http://plus.google.com"), Set.empty)
  )

  val checkedBlacklist: List[Either[URL, Set[Citizen]]] =
    blacklist.map(resource =>
      if (resource.visitors.isEmpty) Left(resource.url)
      else Right(resource.visitors))

  val suspiciousResources = checkedBlacklist.flatMap(_.left.toOption)
  val problemCitizens = checkedBlacklist.flatMap(_.right.toOption).flatten.toSet


}
