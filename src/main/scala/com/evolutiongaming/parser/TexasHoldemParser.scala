package com.evolutiongaming.parser
import com.evolutiongaming.model.{Card, Hand}

object TexasHoldemParser extends InputParser {
  override def parseLine(allCards: List[String]): List[Hand] = {
    val boardCards = Card.fromString(allCards.head)

    allCards.tail.map(Card.fromString).map(handCards => Hand(handCards, handCards ::: boardCards))
  }
}
