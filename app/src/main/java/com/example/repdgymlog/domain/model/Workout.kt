package com.example.repdgymlog.domain.model

data class Workout(
    val workoutId: Long,
    val workoutName: String,
    val exercises: List<Exercise>?
)
