package me.taras.model

data class ChipsColumn(val color: Color = Color.ABSENT, val num: Int = 0) {
    fun isEmpty(): Boolean = num == 0
    fun isBlack(): Boolean = color == Color.BLACK
    fun isWhite(): Boolean = color == Color.WHITE
    fun nonEmpty(): Boolean = !isEmpty()
    fun sameColor(color: Color): Boolean = this.color == color
    fun sameColorOrEmpty(color: Color): Boolean = sameColor(color) || isEmpty()
}

