package com.cumiterbang.mypokedexmobile.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) : BaseDataSource() {

    suspend fun getPokemons(offset: Int) = getResult { apiServices.getPokemons(offset) }
    suspend fun getPokemonDetail(id: Int) = getResult { apiServices.getPokemonDetail(id) }

}