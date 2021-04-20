package me.taras

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
}
