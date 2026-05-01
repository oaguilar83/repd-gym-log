package com.example.repdgymlog.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["workoutId", "exerciseId"])
data class WorkoutExerciseCrossRef(
    val workoutId: Int,
    val exerciseId: Int
)
