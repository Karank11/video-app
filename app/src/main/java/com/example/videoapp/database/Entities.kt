package com.example.videoapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.videoapp.domain.Video

/**
 * DatabaseVideo is the data class that represents a video entity in a RoomDB.
 */
@Entity
data class DatabaseVideo(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String)

/**
 * This is the conversion function that converts list of DatabaseVideo objects into a list of Video objects.
 * This is basically transform data from the database layer to the domain layer.
 */
fun List<DatabaseVideo>.asDomainModel(): List<Video> {
    return map {
        Video(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }
}

