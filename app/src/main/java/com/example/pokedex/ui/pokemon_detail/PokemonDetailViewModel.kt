package com.example.pokedex.ui.pokemon_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel(){

    val loading = MutableLiveData<Boolean>()

    val pokemonDetail = MutableLiveData<PokemonDetail>()
    suspend fun getPokemonDetail(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getPokemonDetail(id)
        if ((response.data?.id?.toInt() ?: 0) == id) {
            pokemonDetail.postValue(response.data!!)
        }
        loading.postValue(false)
    }
}