package me.taras.lib.model

data class GameHistoryWithState(val gameHistory: List<TurnWithDices>, val currentTurnColor: Color, val currentTurnNum: Int, val availableDices: List<Int>, val allDices: List<Int>, val availableTurns: List<List<Turn>>, val turnNum: Int)
