package com.evolutiongaming.parser

import com.evolutiongaming.model.Hand

trait InputParser {
  def parseLine(allCards: List[String]): List[Hand]
}
