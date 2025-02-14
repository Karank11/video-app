package com.example.videoapp.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.videoapp.database.getVideosDatabase
import com.example.videoapp.repository.VideosRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

        companion object {
            const val  WORK_NAME = "RefreshDataWorker"
        }

    override suspend fun doWork(): Result {
        val database = getVideosDatabase(applicationContext)
        val repository = VideosRepository(database)
        return try {
            repository.refreshVideos()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}