package com.example.repdgymlog.data.repository

import com.example.repdgymlog.data.local.dao.ExerciseDao
import com.example.repdgymlog.data.local.dao.WorkoutDao
import com.example.repdgymlog.data.local.entity.ExerciseEntity
import com.example.repdgymlog.data.local.entity.WorkoutEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WorkoutRepositoryImplTest {

    private lateinit var workoutDao: WorkoutDao
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var repository: WorkoutRepositoryImpl

    @Before
    fun setUp() {
        workoutDao = mockk()
        exerciseDao = mockk()
        repository = WorkoutRepositoryImpl(workoutDao, exerciseDao)
    }

    @Test
    fun `getWorkouts returns empty list when dao returns no workouts`() = runTest {
        coEvery { workoutDao.getAllWorkouts() } returns emptyList()

        val result = repository.getWorkouts()

        assertEquals(emptyList<Any>(), result)
    }

    @Test
    fun `getWorkouts maps entities to domain models`() = runTest {
        coEvery { workoutDao.getAllWorkouts() } returns listOf(
            WorkoutEntity(workoutId = 1L, workoutName = "Push Day"),
            WorkoutEntity(workoutId = 2L, workoutName = "Pull Day")
        )

        val result = repository.getWorkouts()

        assertEquals(2, result.size)
        assertEquals(1L, result[0].workoutId)
        assertEquals("Push Day", result[0].workoutName)
        assertEquals(2L, result[1].workoutId)
        assertEquals("Pull Day", result[1].workoutName)
    }

    @Test
    fun `getWorkouts returns domain models with null exercises`() = runTest {
        coEvery { workoutDao.getAllWorkouts() } returns listOf(
            WorkoutEntity(workoutId = 1L, workoutName = "Leg Day")
        )

        val result = repository.getWorkouts()

        assertEquals(null, result[0].exercises)
    }

    @Test
    fun `createWorkout delegates to dao and returns generated id`() = runTest {
        coEvery { workoutDao.insertWorkout(any()) } returns 42L

        val result = repository.createWorkout("Upper Body")

        assertEquals(42L, result)
    }

    @Test
    fun `createWorkout passes correct entity to dao`() = runTest {
        coEvery { workoutDao.insertWorkout(any()) } returns 1L

        repository.createWorkout("Cardio")

        coVerify { workoutDao.insertWorkout(WorkoutEntity(workoutName = "Cardio")) }
    }

    @Test
    fun `deleteWorkout delegates to dao and returns affected rows`() = runTest {
        coEvery { workoutDao.deleteWorkout(5L) } returns 1

        val result = repository.deleteWorkout(5L)

        assertEquals(1, result)
    }

    @Test
    fun `deleteWorkout passes correct id to dao`() = runTest {
        coEvery { workoutDao.deleteWorkout(any()) } returns 1

        repository.deleteWorkout(99L)

        coVerify { workoutDao.deleteWorkout(99L) }
    }

    @Test
    fun `getExercises returns empty list when dao returns no exercises`() = runTest {
        coEvery { exerciseDao.getAllExercises() } returns emptyList()

        val result = repository.getExercises()

        assertEquals(emptyList<Any>(), result)
    }

    @Test
    fun `getExercises maps entities to domain models`() = runTest {
        coEvery { exerciseDao.getAllExercises() } returns listOf(
            ExerciseEntity(exerciseId = 1L, exerciseName = "Bench Press"),
            ExerciseEntity(exerciseId = 2L, exerciseName = "Squat")
        )

        val result = repository.getExercises()

        assertEquals(2, result.size)
        assertEquals(1L, result[0].exerciseId)
        assertEquals("Bench Press", result[0].exerciseName)
        assertEquals(2L, result[1].exerciseId)
        assertEquals("Squat", result[1].exerciseName)
    }

    @Test
    fun `getExercisesForWorkout returns exercises mapped for the given workout`() = runTest {
        coEvery { exerciseDao.getExercisesForWorkout(1L) } returns listOf(
            ExerciseEntity(exerciseId = 10L, exerciseName = "Deadlift")
        )

        val result = repository.getExercisesForWorkout(1L)

        assertEquals(1, result.size)
        assertEquals(10L, result[0].exerciseId)
        assertEquals("Deadlift", result[0].exerciseName)
    }

    @Test
    fun `getExercisesForWorkout returns empty list when workout has no exercises`() = runTest {
        coEvery { exerciseDao.getExercisesForWorkout(any()) } returns emptyList()

        val result = repository.getExercisesForWorkout(7L)

        assertEquals(emptyList<Any>(), result)
    }

    @Test
    fun `getExercisesForWorkout passes correct workoutId to dao`() = runTest {
        coEvery { exerciseDao.getExercisesForWorkout(any()) } returns emptyList()

        repository.getExercisesForWorkout(3L)

        coVerify { exerciseDao.getExercisesForWorkout(3L) }
    }

    @Test
    fun `createExercise delegates to dao and returns generated id`() = runTest {
        coEvery { exerciseDao.insertExercise(any()) } returns 7L

        val result = repository.createExercise("Pull-up")

        assertEquals(7L, result)
    }

    @Test
    fun `createExercise passes correct entity to dao`() = runTest {
        coEvery { exerciseDao.insertExercise(any()) } returns 1L

        repository.createExercise("Overhead Press")

        coVerify { exerciseDao.insertExercise(ExerciseEntity(exerciseName = "Overhead Press")) }
    }

    @Test
    fun `deleteExercise delegates to dao and returns affected rows`() = runTest {
        coEvery { exerciseDao.deleteExercise(3L) } returns 1

        val result = repository.deleteExercise(3L)

        assertEquals(1, result)
    }

    @Test
    fun `deleteExercise passes correct id to dao`() = runTest {
        coEvery { exerciseDao.deleteExercise(any()) } returns 1

        repository.deleteExercise(55L)

        coVerify { exerciseDao.deleteExercise(55L) }
    }

}
