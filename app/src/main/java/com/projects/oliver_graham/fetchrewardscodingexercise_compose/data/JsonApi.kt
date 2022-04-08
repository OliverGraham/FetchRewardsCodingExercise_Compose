package com.projects.oliver_graham.fetchrewardscodingexercise_compose.data

import retrofit2.Call
import retrofit2.http.GET

interface JsonApi {

    // give this annotation the relative url
    @GET("hiring.json")
    fun getItems(): Call<List<Item>>
}