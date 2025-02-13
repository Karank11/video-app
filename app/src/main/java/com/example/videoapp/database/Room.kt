package com.example.videoapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface VideoDao {
    @Query("select * from DatabaseVideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase : RoomDatabase() {
    abstract val videoDao: VideoDao
    private lateinit var INSTANCE: VideosDatabase

    fun getVideosDatabase(context: Context): VideosDatabase {
        synchronized(VideosDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    VideosDatabase::class.java,
                    "videos").build()
            }
        }
        return INSTANCE
    }
}