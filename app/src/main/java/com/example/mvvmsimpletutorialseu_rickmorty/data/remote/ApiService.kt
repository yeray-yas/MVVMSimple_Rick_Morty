package com.example.mvvmsimpletutorialseu_rickmorty.data.remote

import com.example.mvvmsimpletutorialseu_rickmorty.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/character")
    suspend fun fetchCharacters(@Query("page") page:String): CharacterResponse
}