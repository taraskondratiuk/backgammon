package me.taras.lib.model

data class TurnRes(val isTurnSuccessful: Boolean, val isGameOver: Boolean, val isLastForThisPlayer: Boolean, val gameState: GameState)
