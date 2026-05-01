package com.example.repdgymlog.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.repdgymlog.data.local.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity): Int

    @Query("SELECT * FROM exercises")
    suspend fun getAllExercises(): List<ExerciseEntity>
}
