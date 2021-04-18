package me.taras

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GameTest {
    @Test
    fun testGameLongInit() {
        val game = Game.initLong()
        assertEquals(expected = 0, actual = game.gameState().bar.size)
        assertEquals(expected = 24, actual = game.gameState().board.size)
        assertEquals(expected = 15, actual = game.gameState().board[0].num)
        assertEquals(expected = 15, actual = game.gameState().board[23].num)
        assertTrue(game.gameState().availableDices.size <= 4)
        assertTrue(game.gameState().availableDices.size >= 2)
        assertTrue(game.gameState().availableDices.all { it in 1..6 } )
    }
}
