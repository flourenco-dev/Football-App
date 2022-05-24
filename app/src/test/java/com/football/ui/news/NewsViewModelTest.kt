package com.football.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.football.core.Repository
import com.football.model.News
import com.football.news1
import com.football.news2
import com.football.ui.injection.uiModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NewsViewModelTest: KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create {
        Mockito.mock(it.java)
    }
    private val dispatcher = UnconfinedTestDispatcher()
    private val viewModel: NewsViewModel by inject()
    private val repository: Repository by inject()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this).close()
        startKoin {
            modules(uiModule)
        }
        declareMock<Repository>()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun fetchNews_repositoryReturnsTwoNews_newsResultIsCalledWithNewsList() {
        runTest {
            val newsList = listOf(news1, news2)
            var newsResult: List<News>? = null
            viewModel.newsResultObservable.observeForever {
                newsResult = it
            }
            var requestErrorObservableWasCalled = false
            viewModel.requestErrorObservable.observeForever {
                requestErrorObservableWasCalled = true
            }
            whenever(repository.fetchNews()).thenReturn(newsList)

            viewModel.fetchNews()

            Mockito.verify(repository).fetchNews()
            Assert.assertEquals(newsList, newsResult)
            Assert.assertFalse(requestErrorObservableWasCalled)
        }
    }

    @Test
    fun fetchNews_repositoryReturnsEmptyList_requestErrorIsTriggered() {
        runTest {
            val newsList = listOf<News>()
            var newsResult: List<News>? = null
            viewModel.newsResultObservable.observeForever {
                newsResult = it
            }
            var requestErrorObservableWasCalled = false
            viewModel.requestErrorObservable.observeForever {
                requestErrorObservableWasCalled = true
            }
            whenever(repository.fetchNews()).thenReturn(newsList)

            viewModel.fetchNews()

            Mockito.verify(repository).fetchNews()
            Assert.assertNotEquals(newsList, newsResult)
            Assert.assertTrue(requestErrorObservableWasCalled)
        }
    }

    @Test
    fun fetchNews_repositoryThrowsException_requestErrorIsTriggered() {
        runTest {
            var requestErrorObservableWasCalled = false
            viewModel.requestErrorObservable.observeForever {
                requestErrorObservableWasCalled = true
            }
            whenever(repository.fetchNews()).thenThrow(MockitoException(""))

            viewModel.fetchNews()

            Mockito.verify(repository).fetchNews()
            Assert.assertTrue(requestErrorObservableWasCalled)
        }
    }
}