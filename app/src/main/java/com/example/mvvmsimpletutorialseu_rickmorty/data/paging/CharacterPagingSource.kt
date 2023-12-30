package com.example.mvvmsimpletutorialseu_rickmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmsimpletutorialseu_rickmorty.data.repository.MainRepository
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

private const val NO_INTERNET_MESSAGE = "Parece que no tienes los datos de internet activados"


class CharacterPagingSource(
    private val repository: MainRepository,
    private val onError: (Throwable) -> Unit
) : PagingSource<Int, Character>() {

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
            // Manejar el caso específico de UnknownHostException
            if (e is UnknownHostException) {
                onError(Throwable(NO_INTERNET_MESSAGE))
            }
            LoadResult.Error(e)
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> onError(Throwable("Recurso no encontrado"))
                500 -> onError(Throwable("Error del servidor"))
                else -> onError(Throwable("Error de red"))
            }
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }
}

