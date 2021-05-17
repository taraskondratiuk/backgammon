package me.taras.lib

import me.taras.lib.gameimpl.GameLong
import me.taras.lib.model.*

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

        fun initLong(turnHistoryWithState: GameHistoryWithState): Game {
            return GameLong(turnHistoryWithState = turnHistoryWithState)
        }
    }
}



