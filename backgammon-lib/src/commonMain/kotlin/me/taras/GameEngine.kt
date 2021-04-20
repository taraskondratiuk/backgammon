package me.taras

import me.taras.model.Board
import me.taras.model.ChipsColumn
import me.taras.model.Color
import me.taras.model.GameState

class GameEngine(firstTurn: Color, boardInitMethod: (Board) -> Unit) {
    val board = Board()
    val bar: Array<ChipsColumn> = emptyArray()
    var currentTurn: Color
    var availableDices: Array<Int>

    init {
        currentTurn = if (firstTurn == Color.ABSENT) arrayOf(Color.WHITE, Color.BLACK).random() else firstTurn
        availableDices = throwDices()
        boardInitMethod(board)
    }

    fun gameState(): GameState = GameState(board, bar.copyOf(), currentTurn, availableDices.copyOf())


    fun randDiceNum(): Int = (1..6).random()
    fun throwDices(): Array<Int> {
        val dices = arrayOf(randDiceNum(), randDiceNum())
        return if (dices[0] == dices[1]) Array(4) { dices[0] } else dices
    }
}
