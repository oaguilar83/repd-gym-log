package com.example.repdgymlog.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.repdgymlog.data.local.dao.WorkoutDao
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkoutDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var workoutDao: WorkoutDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        workoutDao = db.workoutDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertWorkout_persistsWorkoutInDatabase() = runTest {
        val workout = WorkoutEntity(workoutId = 1, workoutName = "Upper")
        workoutDao.insertWorkout(workout)

        val allWorkouts = workoutDao.getAllWorkouts()

        assertThat(allWorkouts.size, `is`(1))
        assertThat(allWorkouts[0], equalTo(workout))
    }

    @Test
    fun deleteWorkout_returnsZero_whenWorkoutDoesNotExist() = runTest {
        val rowsDeleted = workoutDao.deleteWorkout(workoutId = 999)

        assertThat(rowsDeleted, `is`(0))
    }

    @Test
    fun deleteWorkout_returnsOne_whenWorkoutExists() = runTest {
        val workout = WorkoutEntity(workoutId = 1, workoutName = "Upper")
        workoutDao.insertWorkout(workout)

        val rowsDeleted = workoutDao.deleteWorkout(workout.workoutId)

        assertThat(rowsDeleted, `is`(1))
        assertThat(workoutDao.getAllWorkouts().isEmpty(), `is`(true))
    }

    @Test
    fun getAllWorkouts_returnsEmptyList_whenDatabaseIsEmpty() = runTest {
        val allWorkouts = workoutDao.getAllWorkouts()

        assertThat(allWorkouts.isEmpty(), `is`(true))
    }

    @Test
    fun getAllWorkouts_returnsAllInsertedWorkouts() = runTest {
        val workout1 = WorkoutEntity(workoutId = 1, workoutName = "Push")
        val workout2 = WorkoutEntity(workoutId = 2, workoutName = "Pull")
        val workout3 = WorkoutEntity(workoutId = 3, workoutName = "Legs")
        workoutDao.insertWorkout(workout1)
        workoutDao.insertWorkout(workout2)
        workoutDao.insertWorkout(workout3)

        val allWorkouts = workoutDao.getAllWorkouts()

        assertThat(allWorkouts.size, `is`(3))
        assertThat(allWorkouts.contains(workout1), `is`(true))
        assertThat(allWorkouts.contains(workout2), `is`(true))
        assertThat(allWorkouts.contains(workout3), `is`(true))
    }
}
