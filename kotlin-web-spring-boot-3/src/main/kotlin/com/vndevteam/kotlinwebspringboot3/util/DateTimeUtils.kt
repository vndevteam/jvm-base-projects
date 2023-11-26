package com.vndevteam.kotlinwebspringboot3.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeUtils {
    companion object {
        val zoneId: ZoneId = ZoneId.of("Asia/Tokyo")

        fun getNow(): LocalDateTime {
            return LocalDateTime.now(zoneId)
        }

        fun getSystemNow(format: String): String {
            val df = dateTimeFormatter(format)
            return df.format(LocalDateTime.now())
        }

        fun toZone(source: LocalDateTime): LocalDateTime {
            return LocalDateTime.from(source)
                .atZone(ZoneId.systemDefault()) // current zone
                .withZoneSameInstant(ZoneId.of("Asia/Tokyo")) // new zone
                .toLocalDateTime()
        }

        fun dateTimeFormatter(format: String): DateTimeFormatter {
            return DateTimeFormatter.ofPattern(format, Locale.JAPAN)
        }
    }
}
