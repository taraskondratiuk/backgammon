import * as bg from "backgammon-lib"

export function gameDocToObj(doc) {
    const rowData = doc.data()

    return new bg.me.taras.lib.model.GameFullData(
        rowData.player1Id,
        rowData.player2Id,
        rowData.allDices,
        rowData.availableDices,
        rowData.numViewers,
        rowData.history,
        rowData.availableTurns,
        rowData.currentTurnColor,
        rowData.currentTurnNum,
        rowData.currentTurnPlayerId,
        rowData.createdAt,
        rowData.isFinished,
        rowData.link,
        rowData.params
    )
}