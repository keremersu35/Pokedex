package com.example.pokedex.di

import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.data.repository.PokemonRepositoryImpl
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providePokemonRepository(api: PokemonApi): PokemonRepository {
        return PokemonRepositoryImpl(api)
    }
}