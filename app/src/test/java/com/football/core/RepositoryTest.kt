package com.football.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.football.core.injection.coreModule
import com.football.core.network.ApiHelper
import com.football.core.network.dto.NewsResponse
import com.football.news1
import com.football.news2
import com.football.newsDto1
import com.football.newsDto2
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
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RepositoryTest: KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create {
        Mockito.mock(it.java)
    }

    private val dispatcher = UnconfinedTestDispatcher()
    private val repository: Repository by inject()
    private val apiHelper: ApiHelper by inject()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this).close()
        startKoin {
            modules(coreModule)
        }
        declareMock<ApiHelper>()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun fetchNews_newsResponseContainsTwoNews_newsListWithTwoNewsIsReturned() {
        runTest {
            whenever(apiHelper.getNews()).thenReturn(
                NewsResponse(
                    news = listOf(newsDto1, newsDto2)
                )
            )

            val news = repository.fetchNews()

            Mockito.verify(apiHelper).getNews()
            Assert.assertEquals(listOf(news1, news2), news)
        }
    }
}