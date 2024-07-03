package com.flickr.images.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.flickr.images.data.model.FlickrItem

@Composable
fun ImageCard(item: FlickrItem, onItemClick: (FlickrItem) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onItemClick.invoke(item) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .background(color = Color.White)
        ) {
            Image(
                painter = rememberImagePainter(item.media.m),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp)
                    .clip(RectangleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}