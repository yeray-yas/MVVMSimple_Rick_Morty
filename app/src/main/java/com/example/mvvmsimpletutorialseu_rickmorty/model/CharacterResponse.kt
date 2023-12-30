package com.example.mvvmsimpletutorialseu_rickmorty.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("results")
    val result: List<Character>,
)
