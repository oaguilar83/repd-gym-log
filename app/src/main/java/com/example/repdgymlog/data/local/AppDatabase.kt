package com.example.repdgymlog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repdgymlog.data.local.dao.ExerciseDao
import com.example.repdgymlog.data.local.dao.WorkoutDao
import com.example.repdgymlog.data.local.entity.ExerciseEntity
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import com.example.repdgymlog.data.local.entity.WorkoutExerciseCrossRef

@Database(
    entities = [WorkoutEntity::class, ExerciseEntity::class, WorkoutExerciseCrossRef::class],
    version = 1)
abstract class  AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
}
