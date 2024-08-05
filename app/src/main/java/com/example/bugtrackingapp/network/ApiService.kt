package com.example.bugtrackingapp.network

import com.example.bugtrackingapp.data.UploadResponse
import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("exec?action=create")
    suspend fun saveData(
        @Query("imageUrl") image: String,
        @Query("description") description: String): Call


    @Multipart
    @POST("https://api.escuelajs.co/api/v1/files/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part): UploadResponse


}