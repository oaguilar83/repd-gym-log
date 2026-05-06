package com.example.repdgymlog.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.repdgymlog.data.local.dao.ExerciseDao
import com.example.repdgymlog.data.local.dao.WorkoutDao
import com.example.repdgymlog.data.local.entity.ExerciseEntity
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import com.example.repdgymlog.data.local.entity.WorkoutExerciseCrossRef
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var workoutDao: WorkoutDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        exerciseDao = db.exerciseDao()
        workoutDao = db.workoutDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndQueryExercise() = runTest {
        val exercise = ExerciseEntity(exerciseId = 1, exerciseName = "RDLs")
        exerciseDao.insertExercise(exercise)

        val allExercises = exerciseDao.getAllExercises()
        assertThat(allExercises[0], equalTo(exercise))

        val rowsDeleted = exerciseDao.deleteExercise(exercise.exerciseId)
        assertThat(rowsDeleted, `is`(1))
        assertThat(exerciseDao.getAllExercises().contains(exercise), `is`(false))
    }

    @Test
    fun getExercisesForWorkout() = runTest {
        val workout = WorkoutEntity(workoutId = 1, workoutName = "Push")
        val exercise1 = ExerciseEntity(exerciseId = 1, exerciseName = "Bench Press")
        val exercise2 = ExerciseEntity(exerciseId = 2, exerciseName = "Overhead Press")
        val unrelatedExercise = ExerciseEntity(exerciseId = 3, exerciseName = "Deadlift")

        workoutDao.insertWorkout(workout)
        exerciseDao.insertExercise(exercise1)
        exerciseDao.insertExercise(exercise2)
        exerciseDao.insertExercise(unrelatedExercise)

        workoutDao.insertCrossRef(WorkoutExerciseCrossRef(workoutId = 1, exerciseId = 1))
        workoutDao.insertCrossRef(WorkoutExerciseCrossRef(workoutId = 1, exerciseId = 2))

        val exercises = exerciseDao.getExercisesForWorkout(workoutId = 1)
        assertThat(exercises.size, `is`(2))
        assertThat(exercises.contains(exercise1), `is`(true))
        assertThat(exercises.contains(exercise2), `is`(true))
        assertThat(exercises.contains(unrelatedExercise), `is`(false))
    }
}
