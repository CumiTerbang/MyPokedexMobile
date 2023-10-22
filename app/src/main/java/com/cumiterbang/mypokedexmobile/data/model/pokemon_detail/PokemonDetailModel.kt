package com.cumiterbang.mypokedexmobile.data.model.pokemon_detail

import com.google.gson.annotations.SerializedName

data class PokemonDetailModel(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("height") val height:Int,
    @SerializedName("weight") val weight:Int,
    @SerializedName("abilities") val abilities:ArrayList<PokemonAbilities>,
    @SerializedName("moves") val moves:ArrayList<PokemonMoves>,
    @SerializedName("types") val types:ArrayList<PokemonTypes>,
)
