package br.edu.ifpr.josepher.supertrivia1.model.question.verify

import br.edu.ifpr.josepher.supertrivia1.model.question.Answer

class AnswerVerify(
    val status: String,
    val correct_answer: Answer,
    val score: Int
)