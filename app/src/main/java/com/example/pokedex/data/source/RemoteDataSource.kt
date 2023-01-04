package com.example.pokedex.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.data.remote.PokemonApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/*
class RemoteDataSource @Inject constructor(private val api: PokemonApi) : PagingSource<Int, Response<>>() {
    override suspend fun getTopAnimeCharacters(): NetworkResponseState<TopAnimeCharacterResponse> {
        return try {
            val response = api.getTopCharacters()
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }

    override suspend fun getSingleCharacter(id: String): NetworkResponseState<SingleCharacterResponse> {
        return try {
            val response = api.getSingleCharacterFull(id)
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }
}*/

private const val GITHUB_STARTING_PAGE_INDEX = 1
/*
class GithubPagingSource(
    private val service: GithubService,
    private val query: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER
        return try {
            val response = service.searchRepos(apiQuery, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / 50)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}*/
