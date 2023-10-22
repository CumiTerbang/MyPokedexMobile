package com.cumiterbang.mypokedexmobile.data.model.pokemon_detail

import com.google.gson.annotations.SerializedName

data class PokemonTypes(
    @SerializedName("type") val type: PokemonType? = null
)
