package com.evolutiongaming.comparator

import com.evolutiongaming.model.ordering.CombinationOrdering
import com.evolutiongaming.model.{Combination, Hand}

case object TexasHoldemComparator extends Comparator {
  override def compare(hands: List[Hand]): List[Combination] = {
    hands
      .map(Combination.fromHand)
      .sorted(CombinationOrdering.orElseBy(_.hand.toString))
  }
}
