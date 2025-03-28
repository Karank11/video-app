package com.example.videoapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Retrofit service to fetch the video playlist.
 */
interface VideosApiService {
    @GET("devbytes.json")
    fun getPlaylist(): Deferred<NetworkVideoContainer>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * Main entry point for network access.
 */
object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://devbytes.udacity.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val videosApiService: VideosApiService = retrofit.create(VideosApiService::class.java)
}

