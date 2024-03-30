package com.example.submisionawal.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submisionawal.api.ApiConfig
import com.example.submisionawal.data.DetailUserResponse
import com.example.submisionawal.database.UserFavorite
import com.example.submisionawal.repository.UserFavoriteRespository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val userFavoriteRepository: UserFavoriteRespository = UserFavoriteRespository(application)

    private val _user = MutableLiveData<DetailUserResponse?>()
    val user: MutableLiveData<DetailUserResponse?> = _user


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUser(username: String) {

        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    fun insert(userFavorite: UserFavorite) {
        userFavoriteRepository.insert(userFavorite)
    }

    fun delete(userFavorite: String) {
        userFavoriteRepository.delete(userFavorite)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<UserFavorite> =
        userFavoriteRepository.getFavoriteUserByUsername(username)

}