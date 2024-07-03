package com.flickr.images.repository

import com.flickr.images.data.api.FlickrApiService
import com.flickr.images.data.model.FlickrResponse
import javax.inject.Inject

class FlickrRepository @Inject constructor(
    private val apiService: FlickrApiService
) {
    suspend fun searchPhotos(tags: String): FlickrResponse {
        return apiService.searchPhotos(tags)
    }
}