package com.example.github2.data.remote.retrofit

import com.example.github2.data.remote.response.ResponseDetailUser
import com.example.github2.data.remote.response.ResponseUser
import com.example.github2.data.remote.response.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(@Query("q") value: String): Call<ResponseUser>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String): Call<ResponseDetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<User>>
}