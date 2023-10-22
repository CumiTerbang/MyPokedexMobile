package com.cumiterbang.mypokedexmobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity

@Database(
    entities = [MyPokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyPokemonDatabase : RoomDatabase() {

    abstract fun myPokemonDao(): MyPokemonDAO

    companion object {
        @Volatile
        private var instance: MyPokemonDatabase? = null

        fun getDatabase(context: Context): MyPokemonDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, MyPokemonDatabase::class.java, "mypokemon")
                .fallbackToDestructiveMigration()
                .build()
    }

}