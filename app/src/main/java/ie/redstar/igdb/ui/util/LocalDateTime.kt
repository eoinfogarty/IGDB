package ie.redstar.igdb.ui.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun getFormattedReleaseDate(dateLong: Long?): String {
    return if (dateLong != null) {
        val date = Instant.ofEpochMilli(dateLong * 1000).atZone(ZoneId.systemDefault()).toLocalDate()
        date.format(DateTimeFormatter.ISO_DATE)
    } else {
        "TBC"
    }
}