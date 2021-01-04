import com.evolutiongaming.comparator._
import com.evolutiongaming.parser._
import com.evolutiongaming.util.OutputWriter

import scala.io.StdIn

object Boot extends App {
  val input = Iterator.continually ( StdIn.readLine ).takeWhile ( _.nonEmpty ).foreach ( proceed )

  def proceed(inputLine: String) = {
    val inputToList = inputLine.trim.split ( " " ).toList
    val gameType = inputToList.head

    val (parser, comparator) = gameType match {
      case ("omaha-holdem") => (OmahaInputParser, OmahaComparator)
      case ("texas-holdem") => (TexasHoldemParser, TexasHoldemComparator)
      case ("five-card-draw") => (FiveCardDrawParser, FiveCardDrawComparator)
          }

    val inputs = inputToList.tail
    val hands = parser.parseLine ( inputs )
    val result = comparator.compare ( hands )
    val end = List ( result ).foreach ( OutputWriter.write ( _, print ) )
    println(end)
  }

}