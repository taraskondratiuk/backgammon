package me.taras.model

class TurnNode(val board: Board, val turn: Turn = Turn(0, 0, 0), val depth: Int = 0, val nextTurns: MutableList<TurnNode> = mutableListOf(), var prevTurn: TurnNode? = null) {
}