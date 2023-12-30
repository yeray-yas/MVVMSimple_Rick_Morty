package com.example.mvvmsimpletutorialseu_rickmorty.network

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String
)