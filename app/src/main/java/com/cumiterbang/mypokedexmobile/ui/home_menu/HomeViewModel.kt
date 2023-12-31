package com.cumiterbang.mypokedexmobile.ui.home_menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity
import com.cumiterbang.mypokedexmobile.data.helper.Event
import com.cumiterbang.mypokedexmobile.data.helper.HomeUiState
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDAO
import com.cumiterbang.mypokedexmobile.data.repo.DataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataRepo: DataRepo, private val localDataSource: MyPokemonDAO) : ViewModel() {

    private val _myPokemonCollection = dataRepo.getMyPokemonCollection()
    val myPokemonCollection: LiveData<Resource<List<MyPokemonEntity>>> = _myPokemonCollection

    private val _goCatchPokemon = MutableLiveData<Boolean>()
    val goCatchPokemon:LiveData<Boolean> = _goCatchPokemon

    fun setGoCactchPokemon(catchNow:Boolean){
        _goCatchPokemon.value = catchNow
    }

    fun deleteSelectedPokemon(pokemon:MyPokemonEntity){
        viewModelScope.launch {
            localDataSource.deleteMyPokemon(pokemon)
        }
    }

}