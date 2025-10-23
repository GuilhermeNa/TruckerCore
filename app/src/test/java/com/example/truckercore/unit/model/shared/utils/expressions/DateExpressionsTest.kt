package com.example.truckercore.unit.model.shared.utils.expressions

import com.example.truckercore.data.shared.utils.expressions.toDate
import com.example.truckercore.data.shared.utils.expressions.toLocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class DateExpressionsTest {

    @Test
    fun `should convert Date to LocalDateTime`() {
        // Given a Date object
        val date = Date()

        // When converting to LocalDateTime
        val localDateTime = date.toLocalDateTime()

        // Then the LocalDateTime should not be null and should be equal to the Date in system's default time zone
        assertNotNull(localDateTime)
        val expectedLocalDateTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        val expectedDate = Date.from(expectedLocalDateTime)

        // We check that the Date objects match in the system's default time zone
        assertEquals(date.time, expectedDate.time)
    }

    @Test
    fun `should convert LocalDateTime to Date`() {
        // Given a LocalDateTime object
        val localDateTime = LocalDateTime.now()

        // When converting to Date
        val date = localDateTime.toDate()

        // Then the Date should not be null and should represent the same instant in time as the LocalDateTime
        assertNotNull(date)

        val expectedDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())

        // We check that the Date objects match in the system's default time zone
        assertEquals(date.time, expectedDate.time)
    }

}