package com.flickr.images.ui.screens.view

import com.flickr.images.data.model.FlickrItem

data class SearchViewState(
    val isLoading: Boolean = false,
    val searchResults: List<FlickrItem> = emptyList(),
    val showDetail: FlickrItem? = null
)