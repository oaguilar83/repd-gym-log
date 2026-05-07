package com.example.repdgymlog.domain.repository

import com.example.repdgymlog.domain.model.Exercise
import com.example.repdgymlog.domain.model.Workout

interface WorkoutRepository {
    suspend fun getWorkouts(): List<Workout>
    suspend fun createWorkout(workoutName: String): Long
    suspend fun deleteWorkout(workoutId: Long): Int

    suspend fun getExercises(): List<Exercise>
    suspend fun getExercisesForWorkout(workoutId: Long): List<Exercise>
    suspend fun createExercise(exerciseName: String): Long
    suspend fun deleteExercise(exerciseId: Long): Int
}
