package com.cumiterbang.mypokedexmobile.data.model.pokemon_detail

import com.google.gson.annotations.SerializedName

data class PokemonMoves(
    @SerializedName("move") val move:PokemonMove?=null
)
