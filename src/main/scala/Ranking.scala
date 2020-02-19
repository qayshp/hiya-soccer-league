import com.typesafe.scalalogging.LazyLogging

import scala.io.BufferedSource
import scala.util.matching.Regex

case class LeagueScore(map: scala.collection.mutable.Map[Team, Score])

case class Score(score: Int)

case class Team(name: String)

case class MatchScore(team: Team, score: Score)

case class MatchResult(team1: MatchScore, team2: MatchScore) {
  override val toString: String =
    s"""Match between ${team1.team.name} and ${team2.team.name}
       |Winner: $winner
       |""".stripMargin

  def winner: String = "" match {
    case _ if team1.score.score > team2.score.score => team1.team.name
    case _ if team1.score.score < team2.score.score => team2.team.name
    case _ if team1.score.score == team2.score.score => "tie"
    case _ => "Uhhhh, wat?"
  }

  def loser: String = "" match {
    case _ if team1.score.score < team2.score.score => team1.team.name
    case _ if team1.score.score > team2.score.score => team2.team.name
    case _ if team1.score.score == team2.score.score => "tie"
    case _ => "Uhhhh, wat?"
  }
}

object Ranking extends App with LazyLogging {

  def rank(source: BufferedSource): String = {
    val pattern: Regex = "(.*)\\s(\\d+)".r
    val scores = scala.collection.mutable.Map.empty[String, Int]

    def scoreUpdater(inc: Int): Option[Int] => Option[Int] = {
      case Some(count) => Some(count + inc)
      case None => Some(inc)
    }

    for (line <- source.getLines) {
      val `match`: MatchResult = line.split(',').map(_.trim).flatMap({
        pattern.findFirstIn(_) match {
          case Some(pattern(a: String, b: String)) => Some(MatchScore(Team(a), Score(b.toInt)))
          case None => None
        }
      }).toSeq match {
        case Seq(a, b) => MatchResult(a, b)
      }
      logger.debug(`match`.toString)

      `match`.winner match {
        case "tie" =>
          scores.updateWith(`match`.team1.team.name)(scoreUpdater(1))
          scores.updateWith(`match`.team2.team.name)(scoreUpdater(1))
        case _ =>
          scores.updateWith(`match`.winner)(scoreUpdater(3))
          scores.updateWith(`match`.loser)(scoreUpdater(0))
      }
    }
    logger.debug(scores.toString)
    val ns = scores.toSeq.sortBy(_._2).reverse.zipWithIndex
    logger.debug(ns.toString)

    val sb: StringBuilder = new StringBuilder()

    ns.foreach(x
    => {
      val plural: String = if (x._1._2 != 0) "s"
      else
        " "
      sb.append(s"${x._2 + 1}. ${x._1._1}, ${x._1._2} pt$plural\n")

    })
    sb.result
  }


  try {
    println(rank(io.Source.stdin))
  } finally {
    io.Source.stdin.close
  }


}
