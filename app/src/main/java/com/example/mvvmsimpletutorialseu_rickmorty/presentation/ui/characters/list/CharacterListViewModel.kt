package com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmsimpletutorialseu_rickmorty.data.repository.MainRepository
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import com.example.mvvmsimpletutorialseu_rickmorty.utils.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val repository: MainRepository = MainRepository()
) : ViewModel() {

    private val _charactersLiveData = MutableStateFlow<ViewState<PagingData<Character>>>(ViewState.Loading)
    val charactersLiveData: StateFlow<ViewState<PagingData<Character>>> = _charactersLiveData

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val charactersFlow = Pager(
                    config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
                    pagingSourceFactory = { repository.getCharacterPagingSource { handleError(it) } }
                ).flow.cachedIn(viewModelScope)

                charactersFlow.collect { pagingData ->
                    _charactersLiveData.value = ViewState.Success(pagingData)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(error: Throwable) {
        _charactersLiveData.value = ViewState.Error(error)
    }



    companion object {
        private const val PAGE_SIZE = 20
    }
}

