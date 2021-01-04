package com.evolutiongaming.parser

import com.evolutiongaming.model.{Card, Hand}

object FiveCardDrawParser extends InputParser {
  override def parseLine(allCards: List[String]): List[Hand] = {

    allCards.map(Card.fromString).map(handCards => Hand(handCards, handCards))
  }
}
