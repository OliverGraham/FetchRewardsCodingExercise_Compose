package com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices

import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.ItemDao
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.JsonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

object RetrofitController {

    /**
     * Given a dao and coroutine scope, this will get and convert
     * the JSON objects into an Item-object list, and insert the list
     * into Room.
     */
    fun initialize(dao: ItemDao, scope: CoroutineScope) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonApi: JsonApi = retrofit.create(JsonApi::class.java)
        val call = jsonApi.getItems()

        doEnqueue(theCall = call, dao, scope)
    }

    /**
     *  Handle retrofit's callback
     *  If successful, insert list of converted Json objects into Room
     */
    private fun doEnqueue(theCall: Call<List<Item>>, dao: ItemDao, scope: CoroutineScope) {

        theCall.enqueue(object: Callback<List<Item>> {

            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>){

                // could receive bad response
                if (!response.isSuccessful) {
                    // some helpful message
                    return
                }

                // should have good response (200's); get json objects and all to itemList
                val responseList: List<Item>? = response.body()
                if (responseList != null) {
                    scope.launch { ->

                        // insert list into local SQLite database
                        dao.insertAllItems(responseList)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // maybe error in list, or toast message
                // wasn't able to establish the connection
            }
        })
    }
}