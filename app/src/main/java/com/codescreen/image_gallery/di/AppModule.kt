package com.codescreen.image_gallery.di

import android.os.Debug
import com.codescreen.image_gallery.domain.model.repository.Repository
import com.codescreen.image_gallery.domain.model.repository.RepositoryImpl
import com.codescreen.image_gallery.networking.*
import com.codescreen.image_gallery.networking.responses.*
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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNetworking(api: Api): Networking = NetworkingImpl(api)

    @Singleton
    @Provides
    fun provideRepository(networking: Networking): Repository =
        RepositoryImpl(networking)

    @Singleton
    @Provides
    fun provideRetrofit(): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
            .client(getOkHttpClient())
            .build()
            .create(Api::class.java)

    private fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (Debug.isDebuggerConnected()) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            .build()

    companion object {
        const val NETWORK_REQUEST_TIMEOUT_SECONDS = 15L
        const val BASE_URL = "https://api.flickr.com/services/"
    }
}