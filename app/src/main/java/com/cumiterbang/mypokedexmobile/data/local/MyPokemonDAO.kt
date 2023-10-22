package com.cumiterbang.mypokedexmobile.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity

@Dao
interface MyPokemonDAO {

    @Upsert
    suspend fun upsertMyPokemon(myPokemon:MyPokemonEntity)

    @Delete
    suspend fun deleteMyPokemon(myPokemon:MyPokemonEntity)

    @Query("SELECT * FROM my_pokemon_collection")
    fun getMyPokemonCollection(): LiveData<List<MyPokemonEntity>>
}