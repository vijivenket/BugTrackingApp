package com.example.bugtrackingapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("originalname") val originalname: String,
    @SerializedName("filename") val filename: String,
    @SerializedName("location") val location: String,
)
