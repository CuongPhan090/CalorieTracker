package com.example.tracker_data.repository

import com.example.tracker_data.remote.OpenFoodApi
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class TrackerRepositoryImplTest {

    private lateinit var repository: TrackerRepositoryImpl
    private lateinit var openFoodApi: OpenFoodApi
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        openFoodApi = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()

        repository = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            openFoodApi = openFoodApi
        )
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `search Food returns valid response`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validSearchResponse)
        )
        val result = repository.searchFood(
            query = "beef",
            pageNumber = 1,
            pageSize = 40
        )

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `search Food returns invalid response`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(invalidSearchResponse)
        )
        val result = repository.searchFood(
            query = "beef",
            pageNumber = 1,
            pageSize = 40
        )

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `search Food returns malformed response`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(invalidSearchResponse)
        )
        val result = repository.searchFood(
            query = "beef",
            pageNumber = 1,
            pageSize = 40
        )

        assertThat(result.isFailure).isTrue()
    }
}
