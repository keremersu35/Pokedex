package com.example.pokedex.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.domain.model.Result
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class RemotePagingSource(
    private val repository: PokemonRepository
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = repository.getPokemonList(
                offset = currentPage
            )
            val data = response.data?.results ?: emptyList()
            val nextKey =
                if (data.isEmpty()) null
                else  currentPage + 1
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else -1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

