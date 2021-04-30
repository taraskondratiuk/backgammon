package me.taras

import me.taras.gameimpl.GameLong
import me.taras.model.Color
import me.taras.model.GameState
import me.taras.model.Turn
import me.taras.model.TurnRes

abstract class Game {
    val BOARD_SIZE = 24
    val NUM_CHIPS_ONE_COLOR = 15

    abstract fun gameState(): GameState

    abstract fun makeTurn(turn: Turn): TurnRes
    abstract fun getAvailableTurns(): List<List<Turn>>

    companion object {
        fun initLong(firstTurn: Color = Color.ABSENT): Game {
            return GameLong(firstTurn)
        }
    }
}



