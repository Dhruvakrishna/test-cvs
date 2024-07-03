package com.flickr.images.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.flickr.images.ui.screens.view.SearchViewModel
import com.flickr.images.ui.screens.view.SearchViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(searchViewModel: SearchViewModel) {
    val viewState by searchViewModel.viewState.observeAsState(SearchViewState())

    var query by remember { mutableStateOf("") }
    val context = LocalContext.current

    if (viewState.showDetail != null) {
        ImageDetailScreen(item = viewState.showDetail!!, onBack = {
            searchViewModel.onItemClicked(item = null)
        }, onShare = {
            searchViewModel.onShareClicked(context, item = viewState.showDetail)
        })
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Flickr Image Search") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Gray,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Column {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                            searchViewModel.searchPhotos(query)
                        },
                        label = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    if (viewState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            items(viewState.searchResults) { item ->
                                ImageCard(item) {
                                    searchViewModel.onItemClicked(item = item)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}