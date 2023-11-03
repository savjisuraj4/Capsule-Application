package com.example.capsule_app.model

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)
