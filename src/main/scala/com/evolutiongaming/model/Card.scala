package com.evolutiongaming.model


final case class Card(value: Char, suit: Char, score: Int) {
  override def toString: String = s"$value$suit"
}

object Card {
  def fromString(cards: String): List[Card] = { // из списка, скользящим итератором, создаем объекты и
    cards.toSeq.sliding(2, 2).map(card => {     // загоняем их в один список
      val (value, suit) = (card(0), card(1))
      Card(value.toUpper, suit, value2Score(value))
    }).toList
  }

  def value2Score(value: Char): Int = value.toUpper match {
    case 'T'              => 10
    case 'J'              => 11
    case 'Q'              => 12
    case 'K'              => 13
    case 'A'              => 14
    case ch if (ch.isDigit & ch.asDigit >= 2 & ch.asDigit <= 10) => ch.asDigit
  }
}
