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
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        workoutDao = db.workoutDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndQueryWorkout() = runTest {
        val workout = WorkoutEntity(workoutId = 1, workoutName = "Upper")
        workoutDao.insertWorkout(workout)

        val allWorkouts = workoutDao.getAllWorkouts()
        assertThat(allWorkouts[0], equalTo(workout))

        val rowsDeleted = workoutDao.deleteWorkout(workout.workoutId)
        assertThat(rowsDeleted, `is`(1))
        assertThat(workoutDao.getAllWorkouts().contains(workout), `is`(false))
    }
}
