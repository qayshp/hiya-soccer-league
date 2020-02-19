import org.scalatest.funspec.AnyFunSpec

class RankingTestST extends AnyFunSpec {

  import scala.io.Source

  val sampleInput = Source.fromResource("input-sample.txt")
  val sampleOutput = Source.fromResource("output-expected.txt").bufferedReader()


  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {

        val x = Ranking.rank(sampleInput)

        val str = LazyList.continually(sampleOutput.readLine()).takeWhile(_ != null).mkString("\n")

        assert(x == str)
      }
    }
  }

}
