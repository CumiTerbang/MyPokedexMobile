package com.cumiterbang.mypokedexmobile.data.di

import android.content.Context
import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDAO
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDatabase
import com.cumiterbang.mypokedexmobile.data.remote.ApiServices
import com.cumiterbang.mypokedexmobile.data.remote.RemoteDataSource
import com.cumiterbang.mypokedexmobile.data.repo.DataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideBaseUrl()= ApiUrls.BASE_URL

    fun logOkHttplient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(logOkHttplient())
            .build()
            .create(ApiServices::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context):MyPokemonDatabase = MyPokemonDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMyPokemonDao(db: MyPokemonDatabase):MyPokemonDAO = db.myPokemonDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource,
                          localDataSource: MyPokemonDAO) =
        DataRepo(remoteDataSource, localDataSource)
}