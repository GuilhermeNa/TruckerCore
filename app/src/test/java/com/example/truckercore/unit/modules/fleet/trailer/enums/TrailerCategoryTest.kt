package com.example.truckercore.unit.modules.fleet.trailer.enums

import com.example.truckercore.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TrailerCategoryTest {

    private val expectedEnums = arrayOf(
        TrailerCategory.THREE_AXIS, TrailerCategory.FOUR_AXIS, TrailerCategory.ROAD_TRAIN_FRONT,
        TrailerCategory.ROAD_TRAIN_REAR, TrailerCategory.DOLLY, TrailerCategory.BI_TRUCK_FRONT,
        TrailerCategory.BI_TRUCK_REAR
    )

    @Test
    fun `should contain the expected enums`() {
        // Arrange
        val actualEnums = TrailerCategory.entries.toTypedArray()

        // Act && Assertion
        actualEnums.forEach { enum ->
            assertTrue(expectedEnums.contains(enum))
        }

    }

    @Test
    fun `should contain the expected size`() {
        // Arrange
        val expectedSize = expectedEnums.size
        val actualSize = TrailerCategory.entries.size

        // Assert
        assertEquals(expectedSize, actualSize)

    }

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