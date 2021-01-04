package com.evolutiongaming.model.ordering

import com.evolutiongaming.model._

object CombinationOrdering extends Ordering[Combination] {
  def compare(a: Combination, b: Combination): Int = (a, b) match {
    case (x, y) if (x.strength compare y.strength) != 0 => x.strength compare y.strength
    case (x: HighCard, y: HighCard)                     => compareHighCards(x, y)
    case (x: Pair, y: Pair)                             => comparePairs(x, y)
    case (x: TwoPairs, y: TwoPairs)                     => compareTwoPairs(x, y)
    case (x: ThreeOfKind, y: ThreeOfKind)               => compareThreeOfKind(x, y)
    case (x: Straight, y: Straight)                     => compareStraights(x, y)
    case (x: Flush, y: Flush)                           => compareFlushs(x, y)
    case (x: FullHouse, y: FullHouse)                   => compareFullHouses(x, y)
    case (x: FourOfKind, y: FourOfKind)                 => compareFourOfKinds(x, y)
    case (x: StraightFlush, y: StraightFlush)           => compareStraightFlushs(x, y)
  }

  def compareStraightFlushs(x: StraightFlush, y: StraightFlush): Int = compareCards(x.result.head, y.result.head)

  def compareFourOfKinds(x: FourOfKind, y: FourOfKind): Int = compareKindCombinations(x, y, 4)

  def compareFullHouses(x: FullHouse, y: FullHouse): Int = {
    val (threeOfKind1, pair1) = (x.result.take(3), x.result.takeRight(2))
    val (threeOfKind2, pair2) = (y.result.take(3), y.result.takeRight(2))

    val result = compareCards(threeOfKind1.maxBy(_.score), threeOfKind2.maxBy(_.score))
    if (result != 0) result else compareCards(pair1.maxBy(_.score), pair2.maxBy(_.score))
  }

  def compareFlushs(x: Flush, y: Flush): Int = compareListOfCards(x.result, y.result)

  def compareStraights(x: Straight, y: Straight): Int = compareListOfCards(x.result, y.result)

  def compareThreeOfKind(x: ThreeOfKind, y: ThreeOfKind): Int = compareKindCombinations(x, y, 3)

  def compareTwoPairs(x: TwoPairs, y: TwoPairs): Int = {
    val (pair11, pair12) = (x.result.take(2), x.result.takeRight(2))
    val (pair21, pair22) = (y.result.take(2), y.result.takeRight(2))

    compareCards(pair11.maxBy(_.score), pair21.maxBy(_.score)) match {
      case 0 =>
        val result = compareCards(pair12.maxBy(_.score), pair22.maxBy(_.score))
        if (result != 0) result else compareHighCardsCombination(x, y, 1)
      case result @ _ => result
    }
  }

  def comparePairs(x: Pair, y: Pair): Int = compareKindCombinations(x, y, 2)

  def compareHighCards(x: HighCard, y: HighCard): Int = compareKindCombinations(x, y, 1)

  def compareHighCardsCombination(x: Combination, y: Combination, count: Int): Int = {
    val highCards1 = x.hand.copy(handBlock = getNewHandBlock(x)).handBlock.sortBy(_.score).reverse.take(count)
    val highCards2 = y.hand.copy(handBlock = getNewHandBlock(y)).handBlock.sortBy(_.score).reverse.take(count)

    compareListOfCards(highCards1, highCards2)
  }

  def compareListOfCards(cards1: List[Card], cards2: List[Card]): Int = {
    cards1.sortBy(_.score).reverse
      .zip(cards2.sortBy(_.score).reverse)
      .map{ case (card1, card2) => compareCards(card1, card2) }
      .find(_ != 0).getOrElse(0)
  }

  def compareCards(card1: Card, card2: Card): Int = card1.score compare card2.score

  private def compareKindCombinations(x: Combination, y: Combination, kindCount: Int): Int = {
    compareCards(x.result.head, y.result.head) match {
      case 0 => compareHighCardsCombination(x, y, 5 - kindCount)
      case result @ _ => result
    }
  }

  private def getNewHandBlock(c: Combination): List[Card] = c.hand.handBlock.filterNot(c.result.contains(_))
}
