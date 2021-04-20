package me.taras.gameimpl

import me.taras.Game
import me.taras.GameEngine
import me.taras.model.*


class GameLong(firstTurn: Color = Color.ABSENT): Game() {
    private val engine = GameEngine(firstTurn, ::initBoardMethod)

    private fun initBoardMethod(board: Board) {
        board.put(Color.BLACK, 24, 15)
        board.put(Color.WHITE, 12, 15)
    }

    override fun gameState(): GameState {
        return engine.gameState()
    }

    override fun getAvailableTurns(): Array<Turn> {
        TODO("Not yet implemented")
    }



    override fun makeTurn(from: Int, to: Int): GameState {
        TODO("Not yet implemented")
    }

}
