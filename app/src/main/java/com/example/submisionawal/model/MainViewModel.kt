package com.example.submisionawal.model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submisionawal.api.ApiConfig
import com.example.submisionawal.data.GithubResponse
import com.example.submisionawal.data.ItemsItem

import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response


class MainViewModel: ViewModel() {

    private val _Users = MutableLiveData<ArrayList<ItemsItem>?>()
    val Users: MutableLiveData<ArrayList<ItemsItem>?> = _Users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "MainViewModel"
    }


    init {
        getUsers()
    }

    private fun getUsers(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null)
                        _Users.value = responseBody
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onfailure: ${t.message}")
            }
        })
    }

    fun findUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().findUsers(query)

        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _Users.value = responseBody.items
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}


