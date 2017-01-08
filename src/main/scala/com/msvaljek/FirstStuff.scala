package com.msvaljek

object FirstStuff extends App {

  case class User2(firstName: String, lastName: String, score: Int)

  def advance(xs: List[User2]) = xs match {
    case User2( _, _, score1) :: User2(_, _, score2) :: _ => score1 - score2
    case _ => 0
  }

  val a = FreeUser.unapply(new FreeUser("Marko", 1, 0.8))

  println(a)

  val user: User = new FreeUser("Sparkle", 2, 0.77d)

  val greeting = user match {
    case FreeUser(name, _, p) =>
      if (p > 0.75) s"${name}, what can we do for you today?" else s"Hello ${name}"
    case PremiumUser(name) => s"Welcome back, dear ${name}"
  }

  println(greeting)

  val b = "a" #:: "b" #:: Stream.empty

  println(b)
}

trait User {
  def name: String
  def score: Int
}

class FreeUser(val name: String, val score: Int, val upgradeProbability: Double) extends User
class PremiumUser(val name: String, val score: Int) extends User

object FreeUser {
  def unapply(user: FreeUser): Option[(String, Int, Double)] =
    Some((user.name, user.score, user.upgradeProbability))
}

object PremiumUser {
  def unapply(user: PremiumUser): Option[(String, Int)] =
    Some((user.name, user.score))
}

object premiumCandidate {
  def unapply(user: FreeUser): Boolean = user.upgradeProbability > 0.75
}


