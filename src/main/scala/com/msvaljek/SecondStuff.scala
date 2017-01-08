package com.msvaljek

object SecondStuff extends App {
  println("you gotta to put in the work")

  val xs = 3 :: 6 :: 12 :: Nil

  val k = xs match {
    case List(a, b) => a * b
    case List(a, b, c) => a + b + c
    case _ => 0
  }

  println(k)

  val xs2 = 3 :: 6 :: 12 :: 24 :: Nil

  val kp = xs2 match {
    case List(a, b, _*) => a * b
    case _ => 0
  }

  println(kp)

  def greetWithFirstName(name: String) = name match {
    case GivenNames(firstName, _*) => s"Good morning, ${firstName}!"
    case _ => "Welcome! Please make sure to fill in your name!"
  }

  println(greetWithFirstName("Marko Svaljek"))

  def greet(fullName: String) = fullName match {
    case Names(lastName, firstName, _*) => s"Good morning $firstName, $lastName!"
    case _ => "Welcome, please make sure to fill in your name!"
  }

  println(greet("Justin Beebeƒ The Sucks"))

  case class Player(name: String, score: Int)

  def message(player: Player) = player match {
    case Player(_, score) if score > 100000 => "Get a job dude!"
    case Player(name, _) => s"Hey $name, nice to see you again"
  }

  def printMessage(player: Player) = println(message(player))

  printMessage(Player("Marko", 100))

  def currentPlayer(): Player = Player("Daniel", 3500)

  val player = currentPlayer()

  val Player(name, _) = currentPlayer()

  def gameResult(): (String, Int) = ("Daniel", 3500)

  val (name2, score) = gameResult()

  def gameResults(): Seq[(String, Int)] =
    ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil

  def hallOfFame = for {
    result <- gameResults()
    (name, score) = result
    if (score > 5000)
  } yield name

  def hallOfFame2 = for {
    (name, score) <- gameResults()
    if (score > 5000)
  } yield name

  val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil

  for {
    list @ head :: _ <- lists
  } yield list.size
}

object GivenNames {
  def unapplySeq(name: String): Option[Seq[String]] = {
    val names = name.trim.split(" ")
    if (names.forall(_.isEmpty)) None else Some(names)
  }
}

object Names {
  def unapplySeq(name: String): Option[(String, String, Seq[String])] = {
    val names = name.trim.split(" ")

    if (names.size < 2) None
    else Some((names.last, names.head, names.drop(1).dropRight(1)))
  }
}

//class GivenNames(val name: String, val age:Int)

