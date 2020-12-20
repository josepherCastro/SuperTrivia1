package br.edu.ifpr.josepher.supertrivia1.model.question

import br.edu.ifpr.josepher.supertrivia1.model.category.Category

class Question(
    val question: String,
    val difficulty: String,
    val category: Category,
    val answers: List<Answer>
) {
}