package com.evolutiongaming.util

import com.evolutiongaming.model.{Card, Hand}


object CombinationUtils {
  def getSortedCardsWithNSameValues(hand: Hand, n: Int): Seq[(Int, List[Card])] = {
    hand.handBlock.groupBy(_.score)
      .filter(_._2.size >= n)
      .toSeq
      .sortBy(pair => (pair._2.size, pair._1))(Ordering[(Int, Int)].reverse) //
      .map(pair => (pair._1, pair._2.take(n)))
  }

  def getAllPossibleStraights(hand: Hand): List[List[Card]] = {
    val additionalAces = hand.handBlock.filter(_.score == 14).map(ace => ace.copy(score = 1))

    val newHand = hand.copy(handBlock = additionalAces ::: hand.handBlock)
    val possibleStraights = newHand.handBlock.distinctBy(_.score).sortBy(_.score)(Ordering[Int].reverse).sliding(5, 1)

    val straights = for {
      cards <- possibleStraights if (cards.maxBy(_.score).score - cards.minBy(_.score).score) == 4
      straight = cards.groupBy(_.score).values.toSet if straight.size == 5
    } yield cards

    straights.toList
  }
}
