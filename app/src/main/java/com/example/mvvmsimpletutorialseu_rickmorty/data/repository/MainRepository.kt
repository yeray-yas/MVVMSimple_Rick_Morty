package com.example.mvvmsimpletutorialseu_rickmorty.data.repository

import com.example.mvvmsimpletutorialseu_rickmorty.data.remote.ApiService

class MainRepository(private val apiService: ApiService) {
    // Single source of truth

    // load data from API
fun  getCharacters(page:String) = apiService.fetchCharacters(page)


}