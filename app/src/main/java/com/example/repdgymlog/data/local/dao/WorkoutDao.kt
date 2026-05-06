package com.example.repdgymlog.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import com.example.repdgymlog.data.local.entity.WorkoutExerciseCrossRef

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Insert
    suspend fun insertCrossRef(crossRef: WorkoutExerciseCrossRef)

    @Query("DELETE FROM workouts WHERE workoutId = :workoutId")
    suspend fun deleteWorkout(workoutId: Long): Int

    @Query("SELECT * FROM workouts")
    suspend fun getAllWorkouts(): List<WorkoutEntity>
}
