package com.example.videoapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.videoapp.database.getVideosDatabase
import com.example.videoapp.repository.VideosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class VideoFeedViewModel(application: Application): AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getVideosDatabase(application)
    private val videosRepository = VideosRepository(database)

    init {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true
        if (isConnected) {
            viewModelScope.launch {
                videosRepository.refreshVideos()
            }
        }
    }

    val playlist = videosRepository.videos

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VideoFeedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VideoFeedViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}