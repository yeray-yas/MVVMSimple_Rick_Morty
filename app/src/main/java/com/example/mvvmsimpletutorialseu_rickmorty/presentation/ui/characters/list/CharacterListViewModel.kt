package com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.characters.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmsimpletutorialseu_rickmorty.data.remote.ApiClient
import com.example.mvvmsimpletutorialseu_rickmorty.data.repository.MainRepository
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import com.example.mvvmsimpletutorialseu_rickmorty.utils.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val repository: MainRepository = MainRepository(ApiClient.apiService)
) : ViewModel() {

    private var _charactersLiveData = MutableLiveData<ScreenState<List<Character>?>>()
    val charactersLiveData: LiveData<ScreenState<List<Character>?>>
        get() = _charactersLiveData

    init {
        fetchCharacter()
    }

    private fun fetchCharacter() {

        _charactersLiveData.postValue(ScreenState.Loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = repository.getCharacters("1")
                _charactersLiveData.postValue(ScreenState.Success(client.result))

            } catch (e: Exception) {
                _charactersLiveData.postValue(ScreenState.Error(e.message.toString(), null))

            }
        }
    }
}