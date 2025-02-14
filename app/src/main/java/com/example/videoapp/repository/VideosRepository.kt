package com.example.videoapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.videoapp.database.VideosDatabase
import com.example.videoapp.database.asDomainModel
import com.example.videoapp.domain.Video
import com.example.videoapp.network.Network
import com.example.videoapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository is a regular class that has one or more methods that load data without specifying the
 * data source.
 * It hides the complexity of managing the interactions between the database and the networking code.
 */

class VideosRepository(private val database: VideosDatabase) {

    val videos: LiveData<List<Video>> = database.videoDao.getVideos().map { it.asDomainModel() }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            try {
                val playlist = Network.videosApiService.getPlaylist().await()
                database.videoDao.insertAll(*playlist.asDatabaseModel())
            } catch (e: Exception) {
                Log.e("VideoRepository", "refreshVideos: ", e)
            }
        }
    }
}