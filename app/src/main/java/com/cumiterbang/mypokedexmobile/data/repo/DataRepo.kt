package com.cumiterbang.mypokedexmobile.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDAO
import com.cumiterbang.mypokedexmobile.data.model.PokemonResultsModel
import com.cumiterbang.mypokedexmobile.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DataRepo
@Inject
constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: MyPokemonDAO,
) {

    fun getPokemons(offset: Int) = performGetPokemonsOperation(
        networkCall = { remoteDataSource.getPokemons(offset) }
    )

    private fun <A> performGetPokemonsOperation(
        networkCall: suspend () -> Resource<A>
    ): LiveData<Resource<PokemonResultsModel>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                val result = responseStatus.data!! as PokemonResultsModel
                emit(Resource.success(result))

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }

    fun getMyPokemonCollection() = performGetMyPokemonCollectionOperation(
        databaseQuery = { localDataSource.getMyPokemonCollection() }
    )

    private fun <T> performGetMyPokemonCollectionOperation(
        databaseQuery: () -> LiveData<T>
    ): LiveData<Resource<List<MyPokemonEntity>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) } as LiveData<Resource<List<MyPokemonEntity>>>
            emitSource(source)
        }

}