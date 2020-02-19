import java.io.BufferedReader

import org.scalatest.funspec.AnyFunSpec

import scala.io.BufferedSource

class RankingTestST extends AnyFunSpec {

  import scala.io.Source

  val sampleInput: BufferedSource = Source.fromResource("input-sample.txt")
  val sampleOutput: BufferedReader = Source.fromResource("output-expected.txt").bufferedReader()

  describe("Soccer Ranking") {
    describe("when provided sample input file") {
      it("should match provided output file") {
        val x = Ranking.rank(sampleInput)
        val str = LazyList.continually(sampleOutput.readLine()).takeWhile(_ != null).mkString("\n")
        assert(x == str)
      }
    }
  }
}
