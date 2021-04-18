package me.taras

import me.taras.gameimpl.GameLong
import me.taras.model.Chips
import me.taras.model.Color
import me.taras.model.GameState

abstract class Game {
    val BOARD_SIZE = 24
    val NUM_CHIPS_ONE_COLOR = 15

    abstract fun gameState(): GameState

    abstract fun makeTurn(from: Int, to: Int): GameState

    companion object {
        fun initLong(firstTurn: Color = Color.RANDOM): Game {
            return GameLong(firstTurn)
        }
    }
}



