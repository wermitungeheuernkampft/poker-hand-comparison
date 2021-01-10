import com.evolutiongaming.comparator._
import com.evolutiongaming.parser._
import com.evolutiongaming.util.OutputWriter

import scala.io.StdIn

object Boot extends App {
  val input = Iterator.continually ( StdIn.readLine ).takeWhile ( _.nonEmpty ).foreach ( proceed )

  def proceed(inputLine: String) = {
    val ErrorPrefix = "Error: "
    inputLine.trim.split ( " " ).toList match {
      case ("texas-holdem") :: board :: hands => OutputWriter.write (TexasHoldemComparator.compare(TexasHoldemParser.parseLine(board :: hands)), print)
      case ("omaha-holdem") :: board :: hands => OutputWriter.write (OmahaComparator.compare(OmahaInputParser.parseLine(board :: hands)), print)
      case ("five-card-draw") :: hands        => OutputWriter.write (FiveCardDrawComparator.compare(FiveCardDrawParser.parseLine(hands)), print)
      case x :: _                             => print (ErrorPrefix + "Unrecognized game type")
      case _                                  => print (ErrorPrefix + "Invalid input")
    }
   }
}