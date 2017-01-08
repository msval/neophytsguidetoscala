package com.msvaljek

/**
  * Created by svaljek on 08/01/2017.
  */
object SixthStuff extends App {
  println("Welcome to the error handling")

  case class Customer(age: Int)

  class Cigarettes

  // uncomment if you want to see exception in action
  // buyCigarettes(Customer(12))

  case class UnderAgeException(message: String) extends Exception(message)

  def buyCigarettes(customer: Customer): Cigarettes =
    if (customer.age < 18)
      throw UnderAgeException(s"Customer must be older than 18 but was ${customer.age}")
    else new Cigarettes

  buyCigarettes(Customer(18))


  def youngCustomerBuysCigarettes = {
    val youngCustomer = Customer(15)
    try {
      buyCigarettes(youngCustomer)
      "Yo, here are your cancer sticks! Happy smoking!"
    } catch {
      case UnderAgeException(msg) => msg
    }
  }

  println(youngCustomerBuysCigarettes)

  import scala.util.Try
  import java.net.URL

  def parseUrl(url: String): Try[URL] = Try(new URL(url))

  println(parseUrl("http://msvaljek.blogspot.com"))
  println(parseUrl("some-crazy-page"))

  // this one get boring after you go further with examples
  //val url = parseUrl(Console.readLine("URL: ")) getOrElse new URL("http://google.hr")

  //println(s"You entered: ${url}")

  println(parseUrl("http://msvaljek.blogspot.com").map(_.getProtocol))

  println(parseUrl("gargage").map(_.getProtocol))

  import java.io.InputStream

  def inputStreamFromUrl(url: String): Try[Try[Try[InputStream]]] = parseUrl(url).map{ u =>
    Try(u.openConnection()).map(conn => Try(conn.getInputStream))
  }

  // previous stuff looks awful, let's do some flatMapping

  def betterInputStreamFromUrl(url: String): Try[InputStream] = parseUrl(url).flatMap{u =>
    Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
  }

  import scala.io.Source._

  println(betterInputStreamFromUrl("http://msvaljek.blogspot.com").map(fromInputStream(_).mkString))

  def parseHttpUrl(url: String) = parseUrl(url).filter(_.getProtocol == "http")
  println(parseHttpUrl("http://msvaljek.blogspot.com"))
  println(parseHttpUrl("ftp://mirror.netcologne.de/apache.org"))

  parseHttpUrl("http://www.google.com").foreach(println)

  def getUrlContent(url: String): Try[Iterator[String]] =
    for {
      url <- parseUrl(url)
      connection <- Try(url.openConnection())
      is <- Try(connection.getInputStream)
      source = fromInputStream(is)
    } yield source.getLines()

  getUrlContent("http://google.com").map(_.foreach(println))

  import scala.util.Success
  import scala.util.Failure

  getUrlContent("http://example.org") match {
    case Success(lines) => lines.foreach(println)
    case Failure(ex) => println(s"Problem renderin Url content ${ex}")
  }

  import java.net.MalformedURLException
  import java.io.FileNotFoundException

  val content = getUrlContent("gargabe") recover {
    case e: FileNotFoundException => Iterator("Requested page could not be found")
    case e: MalformedURLException => Iterator("Please make sure you are providing a valid url")
    case _ => Iterator("An unexpected error has occured. We are sorry.")
  }

  print(content.map(_.foreach(println)))
}
