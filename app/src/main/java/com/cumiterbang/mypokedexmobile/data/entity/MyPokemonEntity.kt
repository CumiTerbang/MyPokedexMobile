package com.cumiterbang.mypokedexmobile.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_pokemon_collection")
data class MyPokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nameByCatch: String,
    val name: String,
    val url: String,
)
