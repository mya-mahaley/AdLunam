package com.example.adlunam.ui.trivia
/*
    answer0 is always the correct answer
    correctAnswer is an elaboration on the correct answer
 */
data class TriviaQuestion(
    val question: String,
    val explanation: String,
    val answers: MutableList<Pair<String, Boolean>>,
)