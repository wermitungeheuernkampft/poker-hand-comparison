package com.evolutiongaming.parser

import com.evolutiongaming.model.{Card, Hand}

object OmahaInputParser extends InputParser {
  override def parseLine(allCards: List[String]): List[Hand] = {
    val boardCards: List[Card] = Card.fromString(allCards.head)
    val hands: List[List[Card]] = allCards.tail.map(Card.fromString)

    for {
      hand <- hands
      handSubset <- hand.combinations(2)
      boardSubset <- boardCards.combinations(3)
    } yield Hand(hand, handSubset ::: boardSubset)
  }
}
