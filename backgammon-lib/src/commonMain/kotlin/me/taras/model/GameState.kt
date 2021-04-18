package me.taras.model

data class GameState(val board: Array<Chips>, val bar: Array<Chips>, val currentTurn: Color, val availableDices: Array<Int>)
