package com.msvaljek

/**
  * Created by svaljek on 07/01/2017.
  */
object FifthStuff extends App {
  val greeting: Option[String] = Some("Hello world")

  val greeting1: Option[String] = None

  case class User(
                   id: Int,
                   firstName: String,
                   lastName: String,
                   age: Int,
                   gender: Option[String])

  object UserRepository{
    private val users = Map(
      1 -> User(1, "John", "Doe", 32, Some("male")),
      2 -> User(2, "Johanna", "Doe", 30, Some("female"))
    )

    def findById(id: Int): Option[User] = users.get(id)
    def findAll = users.values
  }

  val user1 = UserRepository.findById(1)
  if (user1.isDefined) {
    println(user1.get.firstName)
  }

  val user = User(2, "Johanna", "Doe", 30, None)
  println(s"Gender ${user.gender.getOrElse("not specified")}")

  user.gender match {
    case Some(gender) => println("Gender: " + gender)
    case None => println("Gender: not specified")
  }

  val gender = user.gender match {
    case Some(gender) => gender
    case None => "not specified"
  }

  println("Gender: " + gender)

  UserRepository.findById(2).foreach(user => println(user.firstName))

  val age = UserRepository.findById(3).map(_.age)

  println(age)

  val gender1 = UserRepository.findById(1).flatMap(_.gender)
  val gender2 = UserRepository.findById(2).flatMap(_.gender)
  val gender3 = UserRepository.findById(3).flatMap(_.gender)

  print(gender1, gender2, gender3)

  val names: List[List[String]] = List(List("John", "Johanna", "Daniel"), List(), List("Doe", "Marko"))

  println(names.map(_.map(_.toUpperCase)))

  println(names.flatMap(_.map(_.toUpperCase)))

  val names2 : List[Option[String]] = List(Some("Johanna"), None, Some("Oh well"))

  println(names2.map(_.map(_.toUpperCase)))

  println(names2.flatMap(_.map(_.toUpperCase)))

}
