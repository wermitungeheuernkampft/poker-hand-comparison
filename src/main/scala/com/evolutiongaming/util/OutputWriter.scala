package com.evolutiongaming.util

import com.evolutiongaming.model.Combination
import com.evolutiongaming.model.ordering.CombinationOrdering

object OutputWriter {
  def write(combinations: List[Combination], outputFunc: String => Unit): Unit = {
    val sortedCombinations = combinations.sorted(CombinationOrdering.orElseBy(_.hand.toString))

    sortedCombinations.sliding(2, 1).foreach(pair => {
      outputFunc(pair.head.hand.toString)
      if (CombinationOrdering.compare(pair.head, pair.last) == 0) outputFunc("=") else outputFunc(" ")
    })

    outputFunc(sortedCombinations.last.hand.toString)
    outputFunc("\n")
  }
}
