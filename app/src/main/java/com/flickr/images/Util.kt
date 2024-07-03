package com.flickr.images

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object Util {
    fun formatDate(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm:ss a")
        val offsetDateTime = OffsetDateTime.parse(dateString)
        return formatter.format(offsetDateTime)
    }

    fun parseDimensionsFromHtml(html: String): Pair<Int, Int>? {
        val regex = """width="(\d+)"\s+height="(\d+)"""".toRegex()
        val matchResult = regex.find(html)
        return if (matchResult != null) {
            val (width, height) = matchResult.destructured
            width.toIntOrNull()?.let { w ->
                height.toIntOrNull()?.let { h ->
                    w to h
                }
            }
        } else {
            null
        }
    }
}