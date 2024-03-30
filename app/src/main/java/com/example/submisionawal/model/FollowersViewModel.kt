package com.example.submisionawal.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submisionawal.api.ApiConfig
import com.example.submisionawal.data.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _follower = MutableLiveData<ArrayList<ItemsItem>?>()
    val follower: MutableLiveData<ArrayList<ItemsItem>?> = _follower

    private val _following = MutableLiveData<ArrayList<ItemsItem>?>()
    val following: MutableLiveData<ArrayList<ItemsItem>?> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowerViewModel"
    }

    fun getFollowers(username: String) {

        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _follower.value = responseBody
                    }
                } else {
                    Log.e(FollowersViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowersViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String) {

        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserFollowings(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _following.value = responseBody
                    }
                } else {
                    Log.e(FollowersViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowersViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }

}