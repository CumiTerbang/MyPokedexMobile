package com.cumiterbang.mypokedexmobile.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.model.PokemonResultsModel
import com.cumiterbang.mypokedexmobile.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DataRepo
@Inject
constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun getPokeomns(offset: Int) = performGetPokemonsOperation(
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

//    fun getPokemonDetail(id: Int) = performGetPokemonDetailOperation(
//        networkCall = { remoteDataSource.getPokemonDetail(id) }
//    )
//
//    fun <A> performGetPokemonDetailOperation(
//        networkCall: suspend () -> Resource<A>
//    ): LiveData<Resource<ArtworkResponseModel>> =
//        liveData(Dispatchers.IO) {
//            emit(Resource.loading())
//
//            val responseStatus = networkCall.invoke()
//            if (responseStatus.status == Resource.Status.SUCCESS) {
//                val result = responseStatus.data!! as ArtworkResponseModel
//                emit(Resource.success(result))
//
//            } else if (responseStatus.status == Resource.Status.ERROR) {
//                emit(Resource.error(responseStatus.message!!))
//            }
//        }

}