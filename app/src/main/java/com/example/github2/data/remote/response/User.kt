package com.example.github2.data.remote.response
import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatar: String,

    @field:SerializedName("id")
    val id: Int,
)