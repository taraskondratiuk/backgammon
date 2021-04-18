package me.taras.gameimpl

import me.taras.Game
import me.taras.GameEngine
import me.taras.model.Chips
import me.taras.model.Color
import me.taras.model.GameState


class GameLong(firstTurn: Color = Color.RANDOM): Game() {
    private val engine = GameEngine(BOARD_SIZE, firstTurn, ::initBoardMethod)

    private fun initBoardMethod(board: Array<Chips>) {
        board[0] = Chips(Color.BLACK, NUM_CHIPS_ONE_COLOR)
        board[BOARD_SIZE - 1] = Chips(Color.WHITE, NUM_CHIPS_ONE_COLOR)
    }

    override fun gameState(): GameState {
        return engine.gameState()
    }

    override fun makeTurn(from: Int, to: Int): GameState {
        TODO("Not yet implemented")
    }

}
