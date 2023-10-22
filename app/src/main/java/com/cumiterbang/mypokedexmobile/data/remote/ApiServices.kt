package com.cumiterbang.mypokedexmobile.data.remote

import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.data.model.PokemonResultsModel
import com.cumiterbang.mypokedexmobile.data.model.pokemon_detail.PokemonDetailModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("pokemon/?${ApiUrls.PAGINATION}")
    suspend fun getPokemons(
        @Query("offset") page: Int?
    ): Response<PokemonResultsModel>

    @GET("pokemon/{name}/")
    suspend fun getPokemonDetail(
        @Path("name") id: String?
    ): Response<PokemonDetailModel>


}