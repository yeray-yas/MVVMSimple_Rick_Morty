package com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmsimpletutorialseu_rickmorty.data.repository.MainRepository
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import kotlinx.coroutines.flow.Flow

class CharacterListViewModel(
    private val repository: MainRepository = MainRepository()
) : ViewModel() {

    val charactersLiveData: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { repository.getCharacterPagingSource() }
    ).flow.cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 20
    }
}


