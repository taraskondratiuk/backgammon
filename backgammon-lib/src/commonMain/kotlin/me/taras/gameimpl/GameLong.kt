package me.taras.gameimpl

import me.taras.Game
import me.taras.GameEngine
import me.taras.model.*


class GameLong(firstTurn: Color = Color.ABSENT): Game() {
    private val engine = GameEngine(firstTurn, ::initBoardMethod, ::generateTurnsMethod)

    fun generateTurnsMethod(board: Board, dices: List<Int>, currentTurnColor: Color, bar: List<ChipsColumn> = emptyList(), isFirst: Boolean): List<List<Turn>> {

        val allDicesPermutations = if (dices.size == 2) listOf(listOf(dices[0], dices[1]), listOf(dices[1], dices[0])) else listOf(dices.toList())

        val allTurns = allDicesPermutations.flatMap { turns ->
            val headNode = TurnNode(board)
            (turns zip turns.indices).forEach { buildTurnsTree(headNode, it.first, it.second, currentTurnColor) }

            lastNodes(headNode).map {
                turns(it)
            }
        }
        val maxLen = allTurns.map { it.size }.maxOrNull() ?: 0
        val maxLenTurns = allTurns.filter { it.size == maxLen }
        val turns = if (maxLen < dices.size) {
            val longestTurn = maxLenTurns.maxByOrNull { it.first().dice }
            listOfNotNull(longestTurn)
        } else maxLenTurns.distinct()
        return if (isFirst) {
            turns.filter { listOfTurns ->
                listOfTurns.filter { turn -> turn.from == headCellByColor(currentTurnColor) }.size < 3
            }
        } else turns.filter { listOfTurns ->
            listOfTurns.filter { turn -> turn.from == headCellByColor(currentTurnColor) }.size < 2
        }
    }

    tailrec fun turns(lastNode: TurnNode, acc: List<Turn> = emptyList()): List<Turn> = if (lastNode.prevTurn == null) acc.reversed() else turns(lastNode.prevTurn!!, acc.plus(lastNode.turn))

    fun lastNodes(head: TurnNode, acc: List<TurnNode> = emptyList()): List<TurnNode> {
        return if (head.nextTurns.isEmpty()) acc.plus(head) else head.nextTurns.flatMap { lastNodes(it, acc) }
    }

    fun headCellByColor(color: Color) = if (color == Color.WHITE) 24 else 12

    fun buildTurnsTree(head: TurnNode, dice: Int, depth: Int, color: Color) {
        if (head.depth == depth) {
            val nextTurns: MutableList<TurnNode> = head.board.getSameColorColumns(color).keys
                .filter { isTurnAvailable(it, dice, head.board) }
                .map { cell ->
                    val newBoard = head.board.copy()
                    val dest = destCell(cell, dice)
                    val remove = !isInsideBoard(color, cell, dice)
                    if (!remove) newBoard.move(cell, dest) else newBoard.remove(cell)
                    val turn = if (remove) Turn(cell, 0, dice) else Turn(cell, dest, dice)
                    TurnNode(newBoard, turn, depth + 1, prevTurn = head)
                }.toMutableList()
            head.nextTurns.addAll(nextTurns)
            return
        } else head.nextTurns.forEach { buildTurnsTree(it, dice, depth, color) }
    }

    fun isTurnAvailable(cell: Int, dice: Int, board: Board): Boolean {
        val color = board.get(cell).color

        fun isNotEmpty() = board.get(cell).nonEmpty()

        fun occupiedCellsSortedList(): List<Int> {
            val occupiedCells = board.getSameColorColumns(color).keys
            val occupiedCellsAfterTurn = if (board.get(cell).num == 1) occupiedCells.minus(cell).plus(destCell(cell, dice)) else occupiedCells.plus(destCell(cell, dice))

            return occupiedCellsAfterTurn.toList().sorted()
        }

        fun getBlock(): List<Int>? {
            return occupiedCellsSortedList().plus(occupiedCellsSortedList())
                .windowed(6)
                .filter { it.contains(destCell(cell, dice)) }
                .firstOrNull { seq ->
                    val diff = seq.last() - seq.first()
                    diff == 5 || diff == -19
                }
        }

        fun isBlock() = getBlock() != null

        fun isBlockAllowed(): Boolean {
            val block = getBlock()!!
            return if (color == Color.BLACK) {
                val lastWhite = board.getWhiteColumns().keys.minOrNull() ?: 0
                !block.any { e ->  e < lastWhite}
            } else {
                val lastBlackNormalized = board.getBlackColumns().keys.map {
                    if (it < 13) it + 24 else it
                }.minOrNull() ?: 0
                !block.map { if (it < 13) it + 24 else it }.any { e -> e < lastBlackNormalized }
            }
        }

        fun areAllAtHome(): Boolean {
            return if (color == Color.BLACK) {
                board.getBlackColumns().keys.all { it in 13..18 }
            } else {
                board.getWhiteColumns().keys.all { it in 1..6 }
            }
        }

        fun isNotAnotherColor() = (board.get(destCell(cell, dice)).sameColorOrEmpty(color))

        fun previousAreEmpty() = ((cell + 1)..(cell + 5)).all { board.get(it).isEmpty() }

        fun canBeThrownOut(): Boolean {
            return isNotEmpty() && areAllAtHome() && isNotAnotherColor() && !isInsideBoard(color, cell, dice) && previousAreEmpty()
        }

        return (isNotEmpty() && isNotAnotherColor() && (isInsideBoard(color, cell, dice)) && (!isBlock() || isBlockAllowed())) || canBeThrownOut()
    }

    fun isInsideBoard(color: Color, cell: Int, dice: Int) = ((color == Color.BLACK) && (destCell(cell, dice) > 13)) || ((color == Color.WHITE) && (!isJumpThrough(cell, dice)))

    fun isJumpThrough(cell: Int, dice: Int) = cell < destCell(cell, dice)

    fun destCell(cell: Int, dice: Int): Int {
        val tmpDest = cell - dice
        return if (tmpDest < 1) {
            24 + tmpDest
        } else tmpDest
    }

    private fun initBoardMethod(board: Board) {
        board.put(Color.BLACK, 24, 15)
        board.put(Color.WHITE, 12, 15)
    }

    override fun gameState(): GameState {
        return engine.gameState()
    }

    override fun getAvailableTurns(): List<List<Turn>> {
        return engine.getAvailableTurns()
    }

    override fun makeTurn(turn: Turn): TurnRes {
        return engine.makeTurn(turn)
    }

}
