package com.msvaljek

object FourthStuff extends App {
  val songTitles = List("The White Hare", "Hey ho", "Lets' go")

  println(songTitles.map(_.toLowerCase))

  val wordFrequencies: List[(String, Int)] = ("habitual", 6) :: ("and", 56) :: ("c-something", 2) ::
    ("cardinally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

  def wordsWithoutOutliners(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)

  println(wordsWithoutOutliners(wordFrequencies))

  def wordsWithoutOutliners2(wordFrequencies: Seq[(String, Int)]) =
    wordFrequencies.filter{ case (_, f) => f > 3 && f < 25} map { case (v, _ ) => v}

  val pf: PartialFunction[(String, Int), String] = {
    case (w, f) if f > 3 && f < 25 => w
  }

  def wordsWithoutOutliners3(wordFrequencies: Seq[(String, Int)]) =
    wordFrequencies.collect{ case(w, f) if f > 3 && f < 25 => w}




}
