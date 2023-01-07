package com.example.pokedex.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.domain.model.Result
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TMDB_STARTING_PAGE_INDEX = 1

class RemotePagingSource(
    private val repository: PokemonRepository
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = repository.getPokemonList(
                offset = pageIndex
            )
            val data = response.data?.results ?: emptyList()
            val nextKey =
                if (data.isEmpty()) {
                    null
                } else {
                    pageIndex + (params.loadSize / 20)
                }
            LoadResult.Page(
                data = data,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
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

