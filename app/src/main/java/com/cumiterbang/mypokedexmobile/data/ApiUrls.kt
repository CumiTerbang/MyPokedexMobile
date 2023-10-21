package com.cumiterbang.mypokedexmobile.data

object ApiUrls {
    const val BASE_URL = "https://pokeapi.co/api/v2/";
    const val PAGINATION = "limit=20" //&offset=0" put offset as query later
    const val POKEMON_LIST_REQUEST = "pokemon/?"
    const val POKEMON_DETAIL_REQUEST = "pokemon/"

    fun getImagePath(pokemonId: String?): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png";
    }

    fun getSpritePath(pokemonId: String?): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png";
    }

}