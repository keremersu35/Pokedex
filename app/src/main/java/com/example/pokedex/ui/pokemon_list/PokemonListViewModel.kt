package com.example.pokedex.ui.pokemon_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonState(
    var pokemonList: Resource<Pokemon>? = null,
)

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonDetailUseCase: PokemonRepository,
) : ViewModel(){
/*
    private val _uiState = MutableStateFlow<Resource<PokemonDetail>>()
    val uiState: StateFlow<Resource<PokemonDetail>> = _uiState.asStateFlow()*/

    private val _uiState = MutableLiveData<Resource<Pokemon>>()
    val uiState: LiveData<Resource<Pokemon>> = _uiState

    init {
        viewModelScope.launch{
            getPokemonList()
        }
    }

/*     suspend fun getPokemonDetail(id: Int){
        val pokemon = getPokemonDetailUseCase.getPokemonDetail(id)
        //pokemon.collect{ poke -> _uiState.update { it.copy(pokemonDetail = poke.data) }}
         _uiState. { it.copy(pokemonDetail = pokemon.data) }
    }*/

    private suspend  fun getPokemonList() = viewModelScope.launch {
        _uiState.postValue(Resource.Loading())
        _uiState.postValue(getPokemonDetailUseCase.getPokemonList(1 * 20))
    }
}