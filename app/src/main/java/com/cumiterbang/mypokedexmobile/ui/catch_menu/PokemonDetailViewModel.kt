package com.cumiterbang.mypokedexmobile.ui.catch_menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDAO
import com.cumiterbang.mypokedexmobile.data.model.PokemonListItemModel
import com.cumiterbang.mypokedexmobile.data.model.PokemonResultsModel
import com.cumiterbang.mypokedexmobile.data.model.pokemon_detail.PokemonDetailModel
import com.cumiterbang.mypokedexmobile.data.repo.DataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val dataRepo: DataRepo, private val localDataSource: MyPokemonDAO): ViewModel(){

    private val _name = MutableLiveData<String>()
    private val _getPokemonDetail = _name.switchMap {name->
        dataRepo.getPokemonDetail(name)
    }
    val getPokemonDetail: LiveData<Resource<PokemonDetailModel>> = _getPokemonDetail

    fun setPokemonName(pokemonName:String){
        _name.value = pokemonName
    }

    fun catchSuccess(catch: PokemonListItemModel){
        var mypokemon = MyPokemonEntity(
            name = catch.name,
            nameByCatch = "catched ${catch.name}",
            url = catch.url
        )
        viewModelScope.launch {
            localDataSource.upsertMyPokemon(mypokemon)
        }
    }

}