package com.cumiterbang.mypokedexmobile.data.model.pokemon_detail

import com.google.gson.annotations.SerializedName

data class PokemonAbilities(
    @SerializedName("ability") val ability: PokemonAbility? = null,
    @SerializedName("slot") val slot: Int,

)
