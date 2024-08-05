package com.example.bugtrackingapp.view

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bugtrackingapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PickImageViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api
    private val mutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val res: Boolean = mutableStateFlow.value


    fun submitBugItem(imageLink: String, description: String) {

        viewModelScope.launch {
            try {
                mutableStateFlow.value = true
                apiService.saveData(imageLink, description)
            } catch (e: Exception) {
                // Handle errors here
            }
        }

    }

    fun uploadImage(multipartBody: MultipartBody.Part, description: String) {
        viewModelScope.launch {
            try {
                val response = apiService.uploadImage(multipartBody)

                response.let {
                    Log.e("imageurl", "response: " + it.location)
                    submitBugItem(it.location, description)

                }
            } catch (e: Exception) {
                //error block
            }
        }
    }
}
