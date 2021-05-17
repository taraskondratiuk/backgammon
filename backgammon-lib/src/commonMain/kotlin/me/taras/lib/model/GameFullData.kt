package me.taras.lib.model

data class GameFullData( //setting default params cause need empty constructor to deserialize from firestore
    val player1Id: String = "",
    val player2Id: String = "",
    val allDices: List<Int> = emptyList(),
    val availableDices: List<Int> = emptyList(),
    val numViewers: Int = 0,
    val history: List<TurnWithDices> = emptyList(),
    val availableTurns: List<List<Turn>> = emptyList(),
    val currentTurnColor: Color = Color.ABSENT,
    val currentTurnNum: Int = 0,
    val currentTurnPlayerId: String = "",
    val createdAt: Long = 0,
    val isFinished: Boolean = false,
    val link: String = "",
    val params: GameParams = GameParams()
)

data class GameParams(val gameId: String = "", val player1Id: String = "", val firstTurnColor: Color = Color.ABSENT, val player1Color: Color = Color.ABSENT, val gameType: String = "", val isByLink: Boolean = false, val isPublic: Boolean = false)
