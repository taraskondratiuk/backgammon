package me.taras

import me.taras.model.Board
import me.taras.model.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class BoardTest {
    @Test
    fun testBoardMoves() {
        val board = Board()

        assertEquals(board.toString(), """
            +------+------+
            |      |      |
            |      |      |
            |      |      |
            |      |      |
            |      |      |
            |      |      |
            |      |      |
            |      |      |
            +------+------+
        """.trimIndent())

        board.put(Color.BLACK, 6, 3)
        board.put(Color.WHITE, 11, 11)
        board.put(Color.BLACK, 19, 12)
        board.put(Color.WHITE, 13, 2)

        assertEquals(board.toString(), """
            +------+------+
            |w     |b     |
            |      |1     |
            |2     |2     |
            |      |      |
            |      |      |
            | 1    |      |
            | 1    |3     |
            | w    |b     |
            +------+------+
        """.trimIndent())

        board.move(6, 5)
        board.move(6, 5)
        board.move(6, 19)
        board.move(13, 12)
        board.move(13, 12)


        assertEquals(board.toString(), """
            +------+------+
            |      |b     |
            |      |1     |
            |      |3     |
            |      |      |
            |      |      |
            | 1    |      |
            |21    | 2    |
            |ww    | b    |
            +------+------+
        """.trimIndent())
    }
}