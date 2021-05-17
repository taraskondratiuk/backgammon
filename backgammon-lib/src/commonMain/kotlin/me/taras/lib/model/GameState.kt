package me.taras.lib.model

class GameState(val board: Board, val bar: Array<ChipsColumn>, var currentTurn: Color, var availableDices: List<Int>, var allDices: List<Int>, var availableTurns: List<List<Turn>>, var turnNum: Int)
