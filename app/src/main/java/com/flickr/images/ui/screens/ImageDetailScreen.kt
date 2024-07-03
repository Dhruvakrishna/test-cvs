package com.flickr.images.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import com.flickr.images.R
import com.flickr.images.Util
import com.flickr.images.data.model.FlickrItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    item: FlickrItem,
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flickr Image Search") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShare) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(7.dp),
                    elevation = CardDefaults.elevatedCardElevation(8.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(item.media.m),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.title,
                    fontSize = 17.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                )

                HtmlViewer(item.description)

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = stringResource(
                        id = R.string.tv_published,
                        Util.formatDate(item.published)
                    ),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = stringResource(id = R.string.tv_author, item.author),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                val widthHeight = Util.parseDimensionsFromHtml(item.description)

                widthHeight?.let {
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        text = stringResource(
                            id = R.string.tv_width_height, it.first, it.second
                        ),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun HtmlViewer(htmlContent: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}