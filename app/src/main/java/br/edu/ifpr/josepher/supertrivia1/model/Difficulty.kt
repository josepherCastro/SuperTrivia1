package br.edu.ifpr.josepher.supertrivia1.model

class Difficulty(
    private val lvl: Int
) {
    val difficulty: String
        get() = when (lvl) {
            1 -> {
                "easy"
            }
            2 -> {
                "medium"
            }
            3 -> {
                "hard"
            }
            else -> {""}
        }
}