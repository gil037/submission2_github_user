package com.example.github2.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseDetailUser(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("public_repos")
    val public_repos: Int,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String
)