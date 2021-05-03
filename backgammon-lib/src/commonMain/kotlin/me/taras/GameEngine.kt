package me.taras

import me.taras.model.*

class GameEngine(firstTurn: Color, boardInitMethod: (Board) -> Unit, val generateTurnsMethod: (Board, List<Int>, Color, List<ChipsColumn>, Boolean) -> List<List<Turn>>) {
    val board = Board()
    private var turnNum = 1
    val bar: Array<ChipsColumn> = emptyArray()
    var currentTurn: Color
    var availableDices: List<Int>
    private var availableTurns: List<List<Turn>>

    init {
        currentTurn = if (firstTurn == Color.ABSENT) arrayOf(Color.WHITE, Color.BLACK).random() else firstTurn
        availableDices = throwDices()
        boardInitMethod(board)
        availableTurns = generateTurnsMethod(board, availableDices, currentTurn, emptyList(), true)
    }

    fun gameState(): GameState = GameState(board, bar.copyOf(), currentTurn, availableDices, availableTurns)

    fun makeTurn(turn: Turn): TurnRes {
        return if (availableTurns.map { it.first() }.contains(turn)) {
            availableTurns = availableTurns.filter { it.first() == turn }
            availableTurns = availableTurns.map { it.drop(1) }
            availableDices.minus(turn.dice)
            board.move(turn.from, turn.to)
            val isGameOver = board.getSameColorColumns(currentTurn).isEmpty()
            val isLastForThisPlayer = if (availableDices.isEmpty()) {
                currentTurn = Color.swap(currentTurn)
                turnNum += 1
                availableTurns = generateTurnsMethod(board, availableDices, currentTurn, emptyList(), turnNum < 3)
                true
            } else false
            TurnRes(true, isGameOver, isLastForThisPlayer)
        } else TurnRes(false, false, false)
    }


    fun getAvailableTurns(): List<List<Turn>> = availableTurns

    fun randDiceNum(): Int = (1..6).random()
    fun throwDices(): List<Int> {
        val dices = listOf(randDiceNum(), randDiceNum())
        return if (dices[0] == dices[1]) List(4) { dices[0] } else dices
    }
}
