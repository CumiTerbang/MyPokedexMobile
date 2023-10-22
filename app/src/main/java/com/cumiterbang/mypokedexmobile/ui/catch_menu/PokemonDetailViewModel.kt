package com.cumiterbang.mypokedexmobile.ui.catch_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDAO
import com.cumiterbang.mypokedexmobile.data.model.PokemonListItemModel
import com.cumiterbang.mypokedexmobile.data.repo.DataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val dataRepo: DataRepo, private val localDataSource: MyPokemonDAO): ViewModel(){

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