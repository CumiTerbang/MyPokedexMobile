package com.cumiterbang.mypokedexmobile.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResultsModel(
    @SerializedName("count") val count:Int,
    @SerializedName("next") val next:String,
    @SerializedName("previous") val previous:String,
    @SerializedName("results") val results:ArrayList<PokemonListItemModel>,
)
