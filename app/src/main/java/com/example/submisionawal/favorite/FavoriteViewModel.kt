package com.example.submisionawal.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submisionawal.database.UserFavorite
import com.example.submisionawal.repository.UserFavoriteRespository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val userFavoriRepository: UserFavoriteRespository = UserFavoriteRespository(application)
    fun getFavoriteUsers(): LiveData<List<UserFavorite>> = userFavoriRepository.getFavoriteUsers()
}