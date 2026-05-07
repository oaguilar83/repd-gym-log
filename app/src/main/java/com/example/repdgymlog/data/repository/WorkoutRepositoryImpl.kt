package com.example.repdgymlog.data.repository

import com.example.repdgymlog.data.local.dao.ExerciseDao
import com.example.repdgymlog.data.local.dao.WorkoutDao
import com.example.repdgymlog.data.local.entity.ExerciseEntity
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import com.example.repdgymlog.data.mapper.toDomain
import com.example.repdgymlog.domain.model.Exercise
import com.example.repdgymlog.domain.model.Workout
import com.example.repdgymlog.domain.repository.WorkoutRepository
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao
) : WorkoutRepository {
    override suspend fun getWorkouts(): List<Workout> =
        workoutDao.getAllWorkouts().map { workoutEntity -> workoutEntity.toDomain() }

    override suspend fun createWorkout(workoutName: String): Long =
        workoutDao.insertWorkout(WorkoutEntity(workoutName = workoutName))

    override suspend fun deleteWorkout(workoutId: Long): Int =
        workoutDao.deleteWorkout(workoutId)

    override suspend fun getExercises(): List<Exercise> =
        exerciseDao.getAllExercises().map { exerciseEntity -> exerciseEntity.toDomain() }

    override suspend fun getExercisesForWorkout(workoutId: Long): List<Exercise> =
        exerciseDao.getExercisesForWorkout(workoutId).map { exerciseEntity -> exerciseEntity.toDomain() }

    override suspend fun createExercise(exerciseName: String): Long =
        exerciseDao.insertExercise(ExerciseEntity(exerciseName = exerciseName))

    override suspend fun deleteExercise(exerciseId: Long): Int =
        exerciseDao.deleteExercise(exerciseId)
}
