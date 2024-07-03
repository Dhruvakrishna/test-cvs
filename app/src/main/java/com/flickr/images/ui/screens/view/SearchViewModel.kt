package com.flickr.images.ui.screens.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flickr.images.R
import com.flickr.images.Util
import com.flickr.images.data.model.FlickrItem
import com.flickr.images.repository.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: FlickrRepository
) : ViewModel() {

    private val _viewState = MutableLiveData(SearchViewState())
    val viewState: LiveData<SearchViewState> get() = _viewState

    fun searchPhotos(tags: String) {
        _viewState.value = _viewState.value?.copy(isLoading = true)
        viewModelScope.launch {
            var searchResults = listOf<FlickrItem>()
            try {
                val response = repository.searchPhotos(tags)
                searchResults = response.items
            } catch (e: Exception) {
                searchResults = emptyList()
            } finally {
                _viewState.value =
                    _viewState.value?.copy(searchResults = searchResults, isLoading = false)
            }
        }
    }

    fun onItemClicked(item: FlickrItem? = null) {
        _viewState.value = _viewState.value?.copy(showDetail = item)
    }

    fun onShareClicked(context: Context, item: FlickrItem?) {
        item?.let {
            val imageUri = item.media.m.let { Uri.parse(it) }
            val title = item.title
            val description = item.description
            val author = context.getString(
                R.string.tv_author, item.author
            )
            val published = context.getString(
                R.string.tv_published, Util.formatDate(item.published)
            )

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, imageUri)
                putExtra(Intent.EXTRA_TEXT, "${title}\n\n$description\n\n$author\n\n$published")
                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, null))
        }
    }


}