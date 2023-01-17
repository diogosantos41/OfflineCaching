package com.dscoding.offlinecaching.di

import android.app.Application
import androidx.room.Room
import com.dscoding.offlinecaching.data.api.SimpsonsQuotesApi
import com.dscoding.offlinecaching.data.data_cache.SimpsonsQuoteCacheDb
import com.dscoding.offlinecaching.data.repository.SimpsonsQuoteRepositoryImpl
import com.dscoding.offlinecaching.domain.repository.SimpsonsQuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(SimpsonsQuotesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSimpsonQuotesApi(retrofit: Retrofit): SimpsonsQuotesApi =
        retrofit.create(SimpsonsQuotesApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): SimpsonsQuoteCacheDb =
        Room.databaseBuilder(app, SimpsonsQuoteCacheDb::class.java, "simpsons_quotes_database")
            .build()

    @Provides
    @Singleton
    fun provideRestaurantRepository(simpsonQuotesApi: SimpsonsQuotesApi, simpsonQuotesCacheDb: SimpsonsQuoteCacheDb): SimpsonsQuotesRepository {
        return SimpsonsQuoteRepositoryImpl(simpsonQuotesApi, simpsonQuotesCacheDb)
    }
}
