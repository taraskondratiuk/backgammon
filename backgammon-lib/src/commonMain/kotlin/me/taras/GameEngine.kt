package me.taras

import me.taras.model.Chips
import me.taras.model.Color
import me.taras.model.GameState

class GameEngine(boardSize: Int, firstTurn: Color, boardInitMethod: (Array<Chips>) -> Unit) {
    val board: Array<Chips> = Array(boardSize) { Chips() }
    val bar: Array<Chips> = emptyArray()
    var currentTurn: Color
    var availableDices: Array<Int>

    init {
        currentTurn = if (firstTurn == Color.RANDOM) arrayOf(Color.WHITE, Color.BLACK).random() else firstTurn
        availableDices = throwDices()
        boardInitMethod(board)
    }

    fun gameState(): GameState = GameState(board.copyOf(), bar.copyOf(), currentTurn, availableDices.copyOf())


    fun randDiceNum(): Int = (1..6).random()
    fun throwDices(): Array<Int> {
        val dices = arrayOf(randDiceNum(), randDiceNum())
        return if (dices[0] == dices[1]) Array(4) { dices[0] } else dices
    }
}
