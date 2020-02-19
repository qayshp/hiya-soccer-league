import com.typesafe.scalalogging.LazyLogging

import scala.io.BufferedSource

case class Score(score: Int)

case class Team(name: String)

case class MatchScore(team: Team, score: Score)

case class MatchResult(team1: MatchScore, team2: MatchScore) extends LazyLogging {
  override val toString: String =
    s"""Match between ${team1.team.name} and ${team2.team.name}
       |Winner: $winner
       |""".stripMargin

  def winner: String = "" match {
    case _ if team1.score.score > team2.score.score => team1.team.name
    case _ if team1.score.score < team2.score.score => team2.team.name
    case _ if team1.score.score == team2.score.score => "tie"
    case _ => logger.error("Unexpected score comparison.")
      "the house"
  }

  def loser: String = "" match {
    case _ if team1.score.score < team2.score.score => team1.team.name
    case _ if team1.score.score > team2.score.score => team2.team.name
    case _ if team1.score.score == team2.score.score => "tie"
    case _ => logger.error("Unexpected score comparison.")
      "all of us"
  }
}

object Ranking extends App with LazyLogging {

  def rank(source: BufferedSource): String = {
    val TeamNameScoreRegex = "(.*)\\s(\\d+)".r
    val teamLeaguePoints = scala.collection.mutable.Map.empty[String, Int]

    def pointAdder(points: Int): Option[Int] => Option[Int] = {
      case Some(count) => Some(count + points)
      case None => Some(points)
    }

    for (line <- source.getLines) {
      val `match`: MatchResult = line.split(',').map(_.trim).flatMap({
        TeamNameScoreRegex.findFirstIn(_) match {
          case Some(TeamNameScoreRegex(a: String, b: String)) => Some(MatchScore(Team(a), Score(b.toInt)))
          case None => None
        }
      }).toSeq match {
        case Seq(a, b) => MatchResult(a, b)
      }
      logger.debug(`match`.toString)

      `match`.winner match {
        case "tie" =>
          teamLeaguePoints.updateWith(`match`.team1.team.name)(pointAdder(1))
          teamLeaguePoints.updateWith(`match`.team2.team.name)(pointAdder(1))
        case _ =>
          teamLeaguePoints.updateWith(`match`.winner)(pointAdder(3))
          teamLeaguePoints.updateWith(`match`.loser)(pointAdder(0)) // important to make sure we show teams with 0 points
      }
    }
    logger.debug(teamLeaguePoints.toString)
    val rankedSortedTeams = teamLeaguePoints.toSeq.groupBy(_._2).toSeq.sortBy(_._1).reverse.flatMap(x => x._2.sortBy(_._1)).zipWithIndex
    logger.debug(rankedSortedTeams.toString)

    val stringBuilder: StringBuilder = new StringBuilder()

    var lastScore = rankedSortedTeams.head._1._2
    var lastRank = 1
    rankedSortedTeams.foreach(x
    => {
      val plural: String = if (x._1._2 != 1) "s" else ""
      val score = x._1._2
      val rank = if (score == lastScore) lastRank else x._2 + 1
      val newLine = if (x._2 < rankedSortedTeams.length - 1) "\n" else ""

      stringBuilder.append(s"$rank. ${x._1._1}, ${score} pt$plural$newLine")

      lastScore = score
      lastRank = rank
    })
    stringBuilder.result
  }

  try {
    println(rank(io.Source.stdin))
  } finally {
    io.Source.stdin.close
  }
}
