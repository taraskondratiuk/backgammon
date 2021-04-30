package me.taras.model

enum class Color {
    BLACK, WHITE, ABSENT;
    companion object {
        fun swap(color: Color): Color {
            return if (color == BLACK) WHITE else BLACK
        }
    }
}
