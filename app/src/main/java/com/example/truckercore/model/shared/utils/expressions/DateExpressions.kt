package com.example.truckercore.model.shared.utils.expressions

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

/**
 * Converts a [Date] object to a [LocalDateTime] in the system's default time zone.
 * @return A [LocalDateTime] object representing the same point in time as the original [Date] object,
 *         adjusted to the system's default time zone.
 */
fun Date.toLocalDateTime(): LocalDateTime {
    val instant = this.toInstant()
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

/**
 * Converts a [LocalDateTime] object to a [Date] in the system's default time zone.
 * @return A [Date] object representing the same point in time as the original [LocalDateTime],
 *         adjusted to the system's default time zone.
 */
fun LocalDateTime.toDate(): Date {
    val atZone = this.atZone(ZoneId.systemDefault())
    return Date.from(atZone.toInstant())
}
