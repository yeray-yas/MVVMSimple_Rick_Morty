package com.example.mvvmsimpletutorialseu_rickmorty.data.repository

import com.example.mvvmsimpletutorialseu_rickmorty.data.paging.CharacterPagingSource
import com.example.mvvmsimpletutorialseu_rickmorty.data.remote.ApiClient.apiService

class MainRepository {
    fun getCharacterPagingSource(onError: (Throwable) -> Unit) = CharacterPagingSource(this, onError)

    suspend fun getCharacters(page: String) = apiService.fetchCharacters(page)
}

