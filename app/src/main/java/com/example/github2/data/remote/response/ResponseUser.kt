package com.example.github2.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @field:SerializedName("items")
    val items: List<User>
)