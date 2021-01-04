package com.evolutiongaming.model

import com.evolutiongaming.util.CombinationUtils


sealed trait Combination {
  def result: List[Card]
  def hand: Hand
  def strength: Int
}
final case class HighCard(result: List[Card], hand: Hand, strength: Int = 0) extends Combination
final case class Pair(result: List[Card], hand: Hand, strength: Int = 1) extends Combination
final case class TwoPairs(result: List[Card], hand: Hand, strength: Int = 2) extends Combination
final case class ThreeOfKind(result: List[Card], hand: Hand, strength: Int = 3) extends Combination
final case class Straight(result: List[Card], hand: Hand, strength: Int = 4) extends Combination
final case class Flush(result: List[Card], hand: Hand, strength: Int = 5) extends Combination
final case class FullHouse(result: List[Card], hand: Hand, strength: Int = 6) extends Combination
final case class FourOfKind(result: List[Card], hand: Hand, strength: Int = 7) extends Combination
final case class StraightFlush(result: List[Card], hand: Hand, strength: Int = 8) extends Combination


case object HighCard {
  def unapply(hand: Hand): Option[HighCard] = {
    val result = List(hand.handBlock.maxBy(_.score))
    Some(HighCard(result, hand))
  }
}

case object Pair { // не понимаю как работает до конца
  def unapply(hand: Hand): Option[Combination] = {
    val pairs = CombinationUtils.getSortedCardsWithNSameValues(hand, 2)
    if (pairs.isEmpty) None else Some(Pair(pairs.head._2, hand))
  }
}

case object TwoPairs {
  def unapply(hand: Hand): Option[Combination] = {
    val pairs = CombinationUtils.getSortedCardsWithNSameValues(hand, 2)
    val combination = TwoPairs(pairs.map(_._2).take(2).toList.flatten, hand)
    if (pairs.size < 2) None else Some(combination)
  }
}

case object ThreeOfKind {
  def unapply(hand: Hand): Option[Combination] = {
    val threeOfKind = CombinationUtils.getSortedCardsWithNSameValues(hand, 3)
    if (threeOfKind.isEmpty) None else Some(ThreeOfKind(threeOfKind.head._2, hand))
  }
}

case object Straight {
  def unapply(hand: Hand): Option[Combination] = {
    val straights = CombinationUtils.getAllPossibleStraights(hand)
    straights.headOption.map(result => Straight(result, hand))
  }
}

case object Flush {
  def unapply(hand: Hand): Option[Combination] = {
    val flushs = hand.handBlock.groupBy(_.suit).values.filter(_.length >= 5).map(_.sortBy(_.score)(Ordering[Int].reverse))
    if (flushs.isEmpty) None else Some(Flush(flushs.maxBy(_.head.score).take(5), hand))
  }
}

case object FullHouse {
  def unapply(hand: Hand): Option[Combination] = {
    ThreeOfKind.unapply(hand) match {
      case None               => None
      case Some(threeOfKind)  =>
        val newHandBlock = hand.handBlock.filterNot(threeOfKind.result.contains(_))
        Pair.unapply(hand.copy(handBlock = newHandBlock)) match {
          case None       => None
          case Some(pair) => Some(FullHouse(threeOfKind.result ::: pair.result, hand))
        }
    }
  }
}

case object FourOfKind {
  def unapply(hand: Hand): Option[Combination] = {
    val fourOfKind = CombinationUtils.getSortedCardsWithNSameValues(hand, 4)
    if (fourOfKind.isEmpty) None else Some(FourOfKind(fourOfKind.head._2, hand))
  }
}

case object StraightFlush {
  def unapply(hand: Hand): Option[Combination] = {
    val newHandBlocks = hand.handBlock.groupBy(_.suit).values.filter(_.length >= 5)
    val newHands = newHandBlocks.map(cards => hand.copy(handBlock = cards))
    val straightFlushs = newHands.flatMap(CombinationUtils.getAllPossibleStraights)

    straightFlushs.headOption.map(result => StraightFlush(result, hand))
  }
}


object Combination {
  def fromHand(hand: Hand): Combination = hand match {
    case StraightFlush(result) => result
    case FourOfKind(result)    => result
    case FullHouse(result)     => result
    case Flush(result)         => result
    case Straight(result)      => result
    case ThreeOfKind(result)   => result
    case TwoPairs(result)      => result
    case Pair(result)          => result
    case HighCard(result)      => result
  }
}
