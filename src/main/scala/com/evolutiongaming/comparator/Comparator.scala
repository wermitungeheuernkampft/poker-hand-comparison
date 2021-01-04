package com.evolutiongaming.comparator

import com.evolutiongaming.model.{Combination, Hand}

trait Comparator {
  def compare(hands: List[Hand]): List[Combination]
}
