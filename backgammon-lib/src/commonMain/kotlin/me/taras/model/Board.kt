package me.taras.model

class Board {
    private val boardArr = Array(24) { ChipsColumn() }

    fun put(color: Color, cell: Int, num: Int = 1) {
        val cellToChange = boardArr[cell - 1]
        boardArr[cell - 1] = cellToChange.copy(color = color, num = cellToChange.num + num)
    }

    fun remove(cell: Int, num: Int = 1): ChipsColumn {
        val cellToChange = boardArr[cell - 1]
        boardArr[cell - 1] = cellToChange.copy(num = cellToChange.num - num)
        if (boardArr[cell - 1].isEmpty()) boardArr[cell - 1] = boardArr[cell - 1].copy(color = Color.ABSENT)
        return cellToChange.copy(num = num)
    }

    fun move(from: Int, to: Int) {
        val removedChips = remove(from)
        put(removedChips.color, to)
    }

    fun get(cell: Int): ChipsColumn {
        return boardArr[cell - 1]
    }

    override fun toString(): String {
        fun colorToSymbol(color: Color): String {
            return when(color) {
                Color.ABSENT -> " "
                Color.BLACK  -> "b"
                Color.WHITE  -> "w"
            }
        }
        fun firstDigit(num: Int): String = if (num < 10) " " else (num / 10).toString()

        fun secondDigit(num: Int): String = if (num == 0) " " else (num % 10).toString()

        val a = boardArr.map { chipsColumn ->
            Triple(
                colorToSymbol(chipsColumn.color),
                firstDigit(chipsColumn.num),
                secondDigit(chipsColumn.num)
            )
        }

        return """
            +------+------+
            |${a[12].first}${a[13].first}${a[14].first}${a[15].first}${a[16].first}${a[17].first}|${a[18].first}${a[19].first}${a[20].first}${a[21].first}${a[22].first}${a[23].first}|
            |${a[12].second}${a[13].second}${a[14].second}${a[15].second}${a[16].second}${a[17].second}|${a[18].second}${a[19].second}${a[20].second}${a[21].second}${a[22].second}${a[23].second}|
            |${a[12].third}${a[13].third}${a[14].third}${a[15].third}${a[16].third}${a[17].third}|${a[18].third}${a[19].third}${a[20].third}${a[21].third}${a[22].third}${a[23].third}|
            |      |      |
            |      |      |
            |${a[11].second}${a[10].second}${a[9].second}${a[8].second}${a[7].second}${a[6].second}|${a[5].second}${a[4].second}${a[3].second}${a[2].second}${a[1].second}${a[0].second}|
            |${a[11].third}${a[10].third}${a[9].third}${a[8].third}${a[7].third}${a[6].third}|${a[5].third}${a[4].third}${a[3].third}${a[2].third}${a[1].third}${a[0].third}|
            |${a[11].first}${a[10].first}${a[9].first}${a[8].first}${a[7].first}${a[6].first}|${a[5].first}${a[4].first}${a[3].first}${a[2].first}${a[1].first}${a[0].first}|
            +------+------+""".trimIndent()
    }
}