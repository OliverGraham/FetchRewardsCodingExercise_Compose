package com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

/**
 * TODO: Create mock repository, check http responses and the
 *       messages produced in response
 */
@RunWith(AndroidJUnit4::class)
class RetrofitControllerTest : TestCase() {

    private lateinit var retrofit: Retrofit
    private lateinit var jsonApi: JsonApi

    @Before
    public override fun setUp() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        jsonApi = retrofit.create(JsonApi::class.java)
    }

    @Test
    fun checkResponseFromFullUrl() {

        // do http get, check response
        val theCall = jsonApi.getItems()
        val response = theCall.execute()
        assert(response.isSuccessful)
    }

    @Test
    fun checkResponseFromFullUrl_Cancel() {
        val theCall = jsonApi.getItems()
        theCall.execute()
        theCall.cancel()
        assert(theCall.isCanceled)
    }
}