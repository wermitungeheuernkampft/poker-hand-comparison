package com.evolutiongaming.model

case class Hand(handCards: List[Card], handBlock: List[Card]) {
  override def toString: String = handCards.map(card => s"${card.value}${card.suit}").mkString
}
