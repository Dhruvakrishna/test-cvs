package com.flickr.images.data.model

data class FlickrResponse(
    val items: List<FlickrItem>
)

data class FlickrItem(
    val title: String,
    val link: String,
    val media: Media,
    val date_taken: String,
    val description: String,
    val published: String,
    val author: String,
    val author_id: String,
    val tags: String
)

data class Media(
    val m: String
)