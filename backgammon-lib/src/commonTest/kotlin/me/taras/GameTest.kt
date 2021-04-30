package me.taras

import me.taras.gameimpl.GameLong
import me.taras.model.Board
import me.taras.model.Color
import me.taras.model.Turn
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GameTest {
    @Test
    fun testGameLongInit() {
        val game = Game.initLong()
        assertEquals(expected = 0, actual = game.gameState().bar.size)
        assertEquals(expected = 15, actual = game.gameState().board.get(24).num)
        assertEquals(expected = 15, actual = game.gameState().board.get(12).num)
        assertTrue(game.gameState().availableDices.size <= 4)
        assertTrue(game.gameState().availableDices.size >= 2)
        assertTrue(game.gameState().availableDices.all { it in 1..6 } )
    }

    @Test
    fun testIsTurnAvailable() {
        val board = Board()
        board.put(Color.WHITE, 24, 10)
        board.put(Color.WHITE, 1, 1)
        board.put(Color.WHITE, 13, 1)
        board.put(Color.BLACK, 12, 10)
        board.put(Color.BLACK, 9, 1)
        board.put(Color.BLACK, 10, 1)
        board.put(Color.BLACK, 11, 1)
        val game = GameLong()
        println(board)

        val turns = game.generateTurnsMethod(board, listOf(3, 5), Color.WHITE, isFirst = false)
        turns.forEach { println(it) }

        assertEquals(4, turns.size)
        assertTrue(turns.contains(listOf(Turn(24, 21, 3), Turn(13, 8, 5))))
        assertTrue(turns.contains(listOf(Turn(24, 21, 3), Turn(21, 16, 5))))
        assertTrue(turns.contains(listOf(Turn(13, 8, 5), Turn(8, 5, 3))))
        assertTrue(turns.contains(listOf(Turn(13, 8, 5), Turn(24, 21, 3))))
    }

    @Test
    fun testIsTurnAvailable2() {
        val board = Board()
        board.put(Color.WHITE, 6, 2)
        board.put(Color.WHITE, 4, 3)
        board.put(Color.WHITE, 5, 2)
        board.put(Color.BLACK, 12, 15)
        val game = GameLong()
        println(board)
        println(1)
        game.generateTurnsMethod(board, listOf(3, 5), Color.WHITE, isFirst = false).forEach { println(it) }
        println(2)


        game.generateTurnsMethod(board, listOf(6, 6, 6, 6), Color.WHITE, isFirst = false).forEach { println(it) }
        println(3)
    }
}
