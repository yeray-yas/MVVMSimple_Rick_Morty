package com.example.mvvmsimpletutorialseu_rickmorty.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/character")
    fun fetchCharacters(@Query("page") page:String): Call<CharacterResponse>
}