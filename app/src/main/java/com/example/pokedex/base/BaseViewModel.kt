package com.example.pokedex.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel() : ViewModel(){

    private var _isBusy = MutableStateFlow(false)
    val isBusy: StateFlow<Boolean> = _isBusy



}