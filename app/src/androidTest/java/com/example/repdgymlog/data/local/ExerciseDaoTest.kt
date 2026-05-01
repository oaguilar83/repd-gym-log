package com.example.repdgymlog.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.repdgymlog.data.local.entity.ExerciseEntity
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

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        exerciseDao = db.exerciseDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndQueryExercise() = runTest {
        val exercise = ExerciseEntity(exerciseId = 1, name = "RDLs")
        exerciseDao.insertExercise(exercise)

        var allExercises = exerciseDao.getAllExercises()
        assertThat(allExercises[0], equalTo(exercise))

        val rowsDeleted = exerciseDao.deleteExercise(exercise)
        assertThat(rowsDeleted, `is`(1))
        assertThat(exerciseDao.getAllExercises().contains(exercise), `is`(false))
    }
}
