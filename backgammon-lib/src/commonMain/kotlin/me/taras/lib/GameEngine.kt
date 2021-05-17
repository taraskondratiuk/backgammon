package me.taras.lib

import me.taras.lib.model.*

class GameEngine(
    firstTurn: Color,
    boardInitMethod: (Board) -> Unit,
    val generateTurnsMethod: (Board, List<Int>, Color, List<ChipsColumn>, Boolean) -> List<List<Turn>>,
    gameHistoryWithState: GameHistoryWithState?
) {

    val board = Board()
    private var turnNum = 1
    val bar: Array<ChipsColumn> = emptyArray()
    var currentTurn: Color
    var availableDices: List<Int>
    var allDices: List<Int>
    private var availableTurns: List<List<Turn>>

    init {
        currentTurn = if (firstTurn == Color.ABSENT) arrayOf(Color.WHITE, Color.BLACK).random() else firstTurn
        availableDices = throwDices()
        allDices = availableDices.toList()
        boardInitMethod(board)
        gameHistoryWithState?.let {
            it.gameHistory.forEach { turnWithDices -> board.move(turnWithDices.turn.from, turnWithDices.turn.to) }
            availableDices = it.availableDices
            allDices = it.allDices
            currentTurn = it.currentTurnColor
            turnNum = it.turnNum
            availableTurns = it.availableTurns
        }
        availableTurns = generateTurnsMethod(board, availableDices, currentTurn, emptyList(), true)
    }

    fun gameState(): GameState = GameState(board, bar.copyOf(), currentTurn, availableDices, allDices, availableTurns, turnNum)

    fun makeTurn(turn: Turn): TurnRes {
        return if (availableTurns.map { it.first() }.contains(turn)) {
            availableTurns = availableTurns.filter { it.first() == turn }
            availableTurns = availableTurns.map { it.drop(1) }
            availableDices.minus(turn.dice)
            board.move(turn.from, turn.to)
            val isGameOver = board.getSameColorColumns(currentTurn).isEmpty()
            val isLastForThisPlayer = if (availableTurns.isEmpty()) {
                currentTurn = Color.swap(currentTurn)
                turnNum += 1
                availableDices = throwDices()
                allDices = availableDices.toList()
                availableTurns = generateTurnsMethod(board, availableDices, currentTurn, emptyList(), turnNum < 3)
                true
            } else false
            TurnRes(true, isGameOver, isLastForThisPlayer, gameState())
        } else TurnRes(false, false, false, gameState())
    }


    fun getAvailableTurns(): List<List<Turn>> = availableTurns

    fun randDiceNum(): Int = (1..6).random()
    fun throwDices(): List<Int> {
        val dices = listOf(randDiceNum(), randDiceNum())
        return if (dices[0] == dices[1]) List(4) { dices[0] } else dices
    }
}
