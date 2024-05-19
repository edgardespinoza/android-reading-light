package com.eespinor.lightreading.reading.di

import android.app.Application
import androidx.room.Room
import com.eespinor.lightreading.common.Constants
import com.eespinor.lightreading.reading.data.local.ReadingDatabase
import com.eespinor.lightreading.reading.data.remote.reading.ReadingApi
import com.eespinor.lightreading.reading.data.remote.room.RoomApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return  OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(moshi:Moshi): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }


    @Provides
    @Singleton
    fun provideReadingApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): ReadingApi {
        return retrofitBuilder.client(okHttpClient).build().create(ReadingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): RoomApi {
        return retrofitBuilder.client(okHttpClient).build().create(RoomApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): ReadingDatabase {
        return Room.databaseBuilder(
            app, ReadingDatabase::class.java, "lightReading.db"
        ).build()
    }

}