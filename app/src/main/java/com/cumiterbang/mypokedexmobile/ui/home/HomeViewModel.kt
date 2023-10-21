package com.cumiterbang.mypokedexmobile.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _myPokemon = MutableLiveData<List<String>>().apply {
        value = emptyList()
    }
    val myPokemon: LiveData<List<String>> = _myPokemon
}