package com.example.truckercore.unit.modules.fleet.trailer.enums

import com.example.truckercore.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TrailerCategoryTest {

    @Test
    fun `test valid enum conversion`() {
        // Valid values for each TrailerCategory
        val validValues = listOf(
            "THREE_AXIS", "FOUR_AXIS", "ROAD_TRAIN_FRONT", "ROAD_TRAIN_REAR",
            "DOLLY", "BI_TRUCK_FRONT", "BI_TRUCK_REAR"
        )

        // For each valid value, ensure the conversion works
        validValues.forEach {
            val category = TrailerCategory.convertString(it)
            assertEquals(TrailerCategory.valueOf(it), category)
        }
    }

    @Test
    fun `test invalid enum conversion`() {
        assertThrows<InvalidEnumParameterException> {
            TrailerCategory.convertString("UNKNOWN_CATEGORY")
        }
    }

    @Test
    fun `test null string conversion`() {
        assertThrows<NullPointerException> {
            TrailerCategory.convertString(null)
        }
    }

    @Test
    fun `test enumExists with valid string`() {
        assertTrue(TrailerCategory.enumExists("THREE_AXIS"))
    }

    @Test
    fun `test enumExists with invalid string`() {
        assertFalse(TrailerCategory.enumExists("UNKNOWN_CATEGORY"))
    }

}