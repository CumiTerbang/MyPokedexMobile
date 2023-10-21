package com.cumiterbang.mypokedexmobile.data.helper

object ApiUrls {
    const val BASE_URL = "https://pokeapi.co/api/v2/";
    const val LIMIT_VALUE = 40
    const val PAGINATION = "limit=$LIMIT_VALUE"
    const val POKEMON_LIST_REQUEST = "pokemon/?"
    const val POKEMON_DETAIL_REQUEST = "pokemon/"

    fun getPokemonIdFromUrl(url: String): String {
        val raw = url.split("pokemon")
        val pokemonId = raw[1].replace("/", "")
        return pokemonId
    }
    fun getImagePath(pokemonId: String?): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png";
    }

    fun getSpritePath(pokemonId: String?): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png";
    }

}