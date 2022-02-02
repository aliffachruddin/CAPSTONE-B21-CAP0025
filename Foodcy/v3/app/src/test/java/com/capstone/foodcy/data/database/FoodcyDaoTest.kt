package com.capstone.foodcy.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.capstone.foodcy.data.database.room.*
import com.capstone.foodcy.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FoodcyDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FoodcyRoomDatabase
    private lateinit var dao: FoodcyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodcyRoomDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.foodcyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUserAnswer() = runBlockingTest {
        val userAnswer = RoomUserAnswer(1, "India", "0", "test")
        dao.insertUserAnswer(userAnswer)

        val allUserAnswer = dao.getUserAnswer("test").getOrAwaitValue()

        assertThat(allUserAnswer).contains(userAnswer)
    }

    @Test
    fun deleteUserAnswer() = runBlockingTest {
        val userAnswer = RoomUserAnswer(1, "India", "0", "test")
        dao.insertUserAnswer(userAnswer)
        dao.deleteUserAnswer(userAnswer)

        val allUserAnswer = dao.getUserAnswer("test").getOrAwaitValue()

        assertThat(allUserAnswer).doesNotContain(userAnswer)
    }

    @Test
    fun updateUserAnswer() = runBlockingTest {
        val userAnswer = RoomUserAnswer(1, "India", "0", "test")
        dao.insertUserAnswer(userAnswer)

        val userAnswerUpdate = RoomUserAnswer(1, "Indonesia", "100", "test")
        dao.updateUserAnswer(userAnswerUpdate)

        val allUserAnswer = dao.getUserAnswer("test").getOrAwaitValue()

        assertThat(allUserAnswer).contains(userAnswerUpdate)
    }

    @Test
    fun insertUserCluster() = runBlockingTest {
        val userCluster = RoomUserCluster("test", "5")
        dao.insertUserCluster(userCluster)

        val allUserCluster = dao.getUserCluster("test").getOrAwaitValue()

        assertThat(allUserCluster).contains(userCluster)
    }

    @Test
    fun deleteUserCluster() = runBlockingTest {
        val userCluster = RoomUserCluster("test", "5")
        dao.insertUserCluster(userCluster)
        dao.deleteUserCluster(userCluster)

        val allUserCluster = dao.getUserCluster("test").getOrAwaitValue()

        assertThat(allUserCluster).doesNotContain(userCluster)
    }

    @Test
    fun updateUserCluster() = runBlockingTest {
        val userCluster = RoomUserCluster("test", "5")
        dao.insertUserCluster(userCluster)

        val userClusterUpdate = RoomUserCluster("test", "10")
        dao.updateUserCluster(userClusterUpdate)

        val allUserCluster = dao.getUserCluster("test").getOrAwaitValue()

        assertThat(allUserCluster).contains(userClusterUpdate)
    }

    @Test
    fun insertUserFavorit() = runBlockingTest {
        val userFavorit = RoomUserFavorit("test", "5")
        dao.insertUserFavorit(userFavorit)

        val allUserFavorit = dao.getUserFavorit("test").getOrAwaitValue()

        assertThat(allUserFavorit).contains(userFavorit)
    }

    @Test
    fun deleteUserFavorit() = runBlockingTest {
        val userFavorit = RoomUserFavorit("test", "5")
        dao.insertUserFavorit(userFavorit)
        dao.deleteUserFavorit(userFavorit)

        val allUserFavorit = dao.getUserFavorit("test").getOrAwaitValue()

        assertThat(allUserFavorit).doesNotContain(userFavorit)
    }

}