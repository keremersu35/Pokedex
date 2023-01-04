package com.example.pokedex.ui.pokemon_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonState(
    var pokemonDetail: Resource<PokemonDetail>? = null,
)

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: PokemonRepository
) : ViewModel(){
/*
    private val _uiState = MutableStateFlow<Resource<PokemonDetail>>()
    val uiState: StateFlow<Resource<PokemonDetail>> = _uiState.asStateFlow()*/

    private val _uiState = MutableLiveData<Resource<PokemonDetail>>()
    val uiState: LiveData<Resource<PokemonDetail>> = _uiState

/*     suspend fun getPokemonDetail(id: Int){
        val pokemon = getPokemonDetailUseCase.getPokemonDetail(id)
        //pokemon.collect{ poke -> _uiState.update { it.copy(pokemonDetail = poke.data) }}
         _uiState. { it.copy(pokemonDetail = pokemon.data) }
    }*/

    suspend  fun getPokemonDetail(id: Int) = viewModelScope.launch {
        _uiState.postValue(Resource.Loading())
        _uiState.postValue(getPokemonDetailUseCase.getPokemonDetail(id))
    }
}