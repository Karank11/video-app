package com.example.videoapp.network

import com.example.videoapp.domain.Video
import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects are responsible for parsing response from the server or formatting objects to
 * send to the server.
 * We should convert these to DomainObjects before using them.
 */

/**
 * NetworkVideoContainer is the data class that holds the list of NetworkVideo objects.
 * The purpose of this class is to deserialize the network response into Kotlin Objects.
 *
 * This class is used to parse the first level of the JSON response which looks like:
 * {
 *   "videos": []
 * }
 */

/**
 * @JsonClass annotation indicates that Moshi should generate a JSON adapter for this class.
 */
@JsonClass(generateAdapter = true)
data class NetworkVideoContainer(val videos: List<NetworkVideo>)


@JsonClass(generateAdapter = true)
data class NetworkVideo(
        val title: String,
        val description: String,
        val url: String,
        val updated: String,
        val thumbnail: String,
        val closedCaptions: String?)

/**
 * Convert Network results into DomainObjects
 */
fun NetworkVideoContainer.asDomainModel(): List<Video> {
    return videos.map {
        Video(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }
}
