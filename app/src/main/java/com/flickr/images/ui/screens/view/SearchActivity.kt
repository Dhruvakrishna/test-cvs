package com.flickr.images.ui.screens.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.flickr.images.ui.screens.ImageListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageListScreen(searchViewModel)
        }
    }
}