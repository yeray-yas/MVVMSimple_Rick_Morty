package com.example.mvvmsimpletutorialseu_rickmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmsimpletutorialseu_rickmorty.data.repository.MainRepository
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(private val repository: MainRepository) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: 1

        return try {
            val response = repository.getCharacters(position.toString())
            val characters = response.result

            LoadResult.Page(
                data = characters,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (characters.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }
}
