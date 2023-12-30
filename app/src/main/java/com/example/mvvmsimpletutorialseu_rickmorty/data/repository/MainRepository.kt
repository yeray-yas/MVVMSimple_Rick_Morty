package com.example.mvvmsimpletutorialseu_rickmorty.data.repository

import com.example.mvvmsimpletutorialseu_rickmorty.data.paging.CharacterPagingSource
import com.example.mvvmsimpletutorialseu_rickmorty.data.remote.ApiClient.apiService

class MainRepository {

    fun getCharacterPagingSource() = CharacterPagingSource(this)

    suspend fun getCharacters(page: String) = apiService.fetchCharacters(page)
}
