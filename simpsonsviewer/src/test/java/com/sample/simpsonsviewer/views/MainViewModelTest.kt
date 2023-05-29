package com.sample.simpsonsviewer.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.networking.domain.CharacterDetails
import com.sample.networking.domain.Results
import com.sample.networking.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val repository = Mockito.mock<RepositoryImpl>()
    private val responseMock = Mockito.mock(Response::class.java)  as Response<Results>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Test request to simpson api then return success`(){

        val dto = Results(listOf(CharacterDetails("image", "title", "description")))

        runTest {
            Mockito.`when`(responseMock.isSuccessful).thenReturn(true)
            Mockito.`when`(responseMock.body()).thenReturn(dto)
            Mockito.`when`(repository.getSimpsonCharacterList()).thenReturn(responseMock)
            val viewModel = MainViewModel(repository)
            viewModel.getSimpsonCharacterList()
            advanceUntilIdle()
            viewModel.characterList.observeForever {
                assertEquals(it, dto)
            }
        }
    }

    @Test
    fun `Test request to simpson api then return error`(){
        val message = "Error test"
        runTest {
            Mockito.`when`(responseMock.isSuccessful).thenReturn(false)
            Mockito.`when`(responseMock.message()).thenReturn(message)
            Mockito.`when`(repository.getSimpsonCharacterList()).thenReturn(responseMock)
            val viewModel = MainViewModel(repository)
            viewModel.getSimpsonCharacterList()
            advanceUntilIdle()
            viewModel.errorMessage.observeForever {
                assertEquals(it, message)
            }
        }
    }
}