package com.example.repdgymlog.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repdgymlog.data.local.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM exercises WHERE exerciseId = :exerciseId")
    suspend fun deleteExercise(exerciseId: Long): Int

    @Query("SELECT * FROM exercises")
    suspend fun getAllExercises(): List<ExerciseEntity>

    @Query("""
        SELECT e.* FROM exercises e
        INNER JOIN workout_exercise_cross_ref r ON e.exerciseId = r.exerciseId
        WHERE r.workoutId = :workoutId
    """)
    suspend fun getExercisesForWorkout(workoutId: Long): List<ExerciseEntity>
}
