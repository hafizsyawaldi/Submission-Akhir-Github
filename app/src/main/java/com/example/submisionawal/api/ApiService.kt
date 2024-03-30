package com.example.submisionawal.api

import com.example.submisionawal.data.DetailUserResponse
import com.example.submisionawal.data.GithubResponse
import com.example.submisionawal.data.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers():Call<ArrayList<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("search/users")
    fun findUsers(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}/following")
    fun getUserFollowings(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

}