package com.example.videoapp.domain

import com.example.videoapp.util.smartTruncate


/**
 * DomainObjects are plain Kotlin data classes that represent the things in our app.
 * These are the objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects (DataTransferObjects) that parse or prepare network calls
 */


/**
 * Video represent a video that can be played.
 */
data class Video(val title: String,
                 val description: String,
                 val url: String,
                 val updated: String,
                 val thumbnail: String) {

    val shortDescription: String
        get() = description.smartTruncate(200)

}
