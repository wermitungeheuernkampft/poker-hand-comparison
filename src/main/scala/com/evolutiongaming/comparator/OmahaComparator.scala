package com.evolutiongaming.comparator

import com.evolutiongaming.model.{Combination, Hand}
import com.evolutiongaming.model.ordering.CombinationOrdering

case object OmahaComparator extends Comparator {
  override def compare(hands: List[Hand]): List[Combination] = {
    val bestFromHand = hands
      .groupBy(_.handCards)
      .map {case (hand, variations) => variations.map(Combination.fromHand).sorted(CombinationOrdering.reverse)}
      .map(_.head)
      .toList

    bestFromHand.sorted(CombinationOrdering.orElseBy(_.hand.toString))
  }
}