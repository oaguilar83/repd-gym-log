package com.example.repdgymlog.data.mapper

import com.example.repdgymlog.data.local.entity.ExerciseEntity
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import com.example.repdgymlog.domain.model.Exercise
import com.example.repdgymlog.domain.model.Workout

fun WorkoutEntity.toDomain(): Workout = Workout(
    workoutId = workoutId,
    workoutName = workoutName,
    exercises = null
)

fun Workout.toEntity(): WorkoutEntity = WorkoutEntity(
    workoutId = workoutId,
    workoutName = workoutName
)

fun ExerciseEntity.toDomain(): Exercise = Exercise(
    exerciseId = exerciseId,
    exerciseName = exerciseName
)

fun Exercise.toEntity(): Exercise = Exercise(
    exerciseId = exerciseId,
    exerciseName = exerciseName
)
