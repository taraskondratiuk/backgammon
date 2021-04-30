package me.taras.model

class GameState(val board: Board, val bar: Array<ChipsColumn>, var currentTurn: Color, var availableDices: List<Int>, var availableTurns: List<List<Turn>>)
