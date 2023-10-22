package com.cumiterbang.mypokedexmobile.ui.catch_menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.model.PokemonResultsModel
import com.cumiterbang.mypokedexmobile.data.repo.DataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatchViewModel @Inject constructor(private val dataRepo: DataRepo): ViewModel() {

    private val _offset = MutableLiveData<Int>()
    private val _getPokemons = _offset.switchMap {offset->
        dataRepo.getPokemons(offset)
    }
    val getPokemons: LiveData<Resource<PokemonResultsModel>> = _getPokemons

    fun init(){
        _offset.value = 0
    }

    fun setOffset(offset: Int) {
        _offset.value = offset
    }
}