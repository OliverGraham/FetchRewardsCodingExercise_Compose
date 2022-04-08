package com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices

import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.JsonApi
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

class RetrofitController {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _items = MutableStateFlow(listOf<Item>())
    fun getItems(): MutableStateFlow<List<Item>> = _items

    fun initializeItems() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonApi: JsonApi = retrofit.create(JsonApi::class.java)
        val call = jsonApi.getItems()

        // retrofit has a built-in asynchronous handler
        doEnqueue(theCall = call, itemList = _items)
    }

    /**
     *  Handle retrofit's callback
     *  If successful, add list of converted Json objects to itemList
     */
    private fun doEnqueue(theCall: Call<List<Item>>, itemList: MutableStateFlow<List<Item>>) {

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
                    scope.launch {
                        itemList.emit(responseList)
                    }

                    // itemList.addAll(responseList)
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // maybe error in list, or toast message
                // wasn't able to establish the connection
            }
        })
    }
}