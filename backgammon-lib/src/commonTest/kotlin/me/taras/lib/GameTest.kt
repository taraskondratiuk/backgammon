package me.taras.lib

import me.taras.lib.gameimpl.GameLong
import me.taras.lib.model.Board
import me.taras.lib.model.Color
import me.taras.lib.model.Turn
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

        val turns = game.generateTurnsMethod(board, listOf(3, 5), Color.WHITE, isFirst = false)

        assertEquals(4, turns.size)
        assertTrue(turns.contains(listOf(Turn(24, 21, 3), Turn(13, 8, 5))))
        assertTrue(turns.contains(listOf(Turn(24, 21, 3), Turn(21, 16, 5))))
        assertTrue(turns.contains(listOf(Turn(13, 8, 5), Turn(8, 5, 3))))
        assertTrue(turns.contains(listOf(Turn(13, 8, 5), Turn(24, 21, 3))))
    }

    @Test
    fun testNoTurnsAvailable() {
        val board = Board()
        board.put(Color.BLACK, 12, 15)
        board.put(Color.WHITE, 24, 15)

        val game = GameLong()
        board.put(Color.BLACK, 23)
        val turns1 = game.generateTurnsMethod(board, listOf(1, 1, 1, 1), Color.WHITE, isFirst = false)
        assertEquals(emptyList(), turns1)
    }

    @Test
    fun testIsTurnAvailableFromHeadNotFirst() {
        val board = Board()
        board.put(Color.BLACK, 12, 15)
        board.put(Color.WHITE, 24, 15)

        val game = GameLong()
        val turns1 = game.generateTurnsMethod(board, listOf(6, 6, 6, 6), Color.WHITE, isFirst = false)
        assertEquals(listOf(listOf(Turn(24, 18, 6))), turns1)


        board.put(Color.BLACK, 22, 2)
        board.put(Color.BLACK, 21, 2)
        val turns2 = game.generateTurnsMethod(board, listOf(1, 2), Color.WHITE, isFirst = false)
        assertEquals(listOf(listOf(Turn(24, 23, 1))), turns2)
    }

    @Test
    fun testIsTurnAvailableFromHeadFirst() {
        val board = Board()
        board.put(Color.BLACK, 12, 15)
        board.put(Color.WHITE, 24, 15)

        val game = GameLong()
        val turns1 = game.generateTurnsMethod(board, listOf(6, 6, 6, 6), Color.WHITE, isFirst = true)
        assertEquals(listOf(listOf(Turn(24, 18, 6), Turn(24, 18, 6))), turns1)

        val turns2 = game.generateTurnsMethod(board, listOf(3, 3, 3, 3), Color.WHITE, isFirst = true)
        assertTrue(turns2.contains(listOf(Turn(24, 21, 3), Turn(21, 18, 3), Turn(18, 15, 3), Turn(24, 21, 3))))
        assertTrue(turns2.all { it.size == 4 })

        board.put(Color.BLACK, 18)

        val turns3 = game.generateTurnsMethod(board, listOf(3, 3, 3, 3), Color.WHITE, isFirst = true)
        assertEquals(listOf(listOf(Turn(24, 21, 3))), turns3)

        board.put(Color.BLACK, 21)

        val turns4 = game.generateTurnsMethod(board, listOf(3, 3, 3, 3), Color.WHITE, isFirst = true)
        assertEquals(emptyList(), turns4)
    }

    @Test
    fun testIsTurnAvailableToBlock() {
        val board = Board()
        board.put(Color.BLACK, 12, 15)
        board.put(Color.WHITE, 24, 15)
        board.put(Color.WHITE, 18)
        board.put(Color.WHITE, 17)
        board.put(Color.WHITE, 16)
        board.put(Color.WHITE, 15)
        board.put(Color.WHITE, 14)


        val game = GameLong()
        val turns1 = game.generateTurnsMethod(board, listOf(5), Color.WHITE, isFirst = false)
        assertTrue(turns1.flatMap { it.filter { turn -> turn.from == 24  } }.isEmpty())

        board.put(Color.BLACK, 13)
        val turns2 = game.generateTurnsMethod(board, listOf(5), Color.WHITE, isFirst = false)
        assertTrue(turns2.flatMap { it.filter { turn -> turn.from == 24  } }.isNotEmpty())
    }

}
