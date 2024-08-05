package com.example.bugtrackingapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private
    const val BASE_URL = "https://script.google.com/macros/s/AKfycbyU5hAyzni1bqpQm2da2u9CtG7ZmygY91PsNwWi0R5G5sCAZ9nwoyq-zLgnRhl2fcR2/"
    val api: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

