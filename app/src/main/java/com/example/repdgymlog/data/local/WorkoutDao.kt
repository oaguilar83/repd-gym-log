package com.example.repdgymlog.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import com.example.repdgymlog.data.local.entity.WorkoutWithExercises

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity): Int

    @Query("SELECT * FROM workouts")
    suspend fun getAllWorkouts(): List<WorkoutEntity>

    @Transaction
    @Query("SELECT * FROM workouts")
    suspend fun getWorkoutWithExercises(): List<WorkoutWithExercises>
}
