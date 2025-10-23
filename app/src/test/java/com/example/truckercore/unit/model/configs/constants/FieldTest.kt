package com.example.truckercore.unit.model.configs.constants

import com.example.truckercore.core.config.enums.Field
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertTrue

class FieldTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val expectedCollection = listOf(
        com.example.truckercore.core.config.enums.Field.ID,
        com.example.truckercore.core.config.enums.Field.COMPANY_ID,
        com.example.truckercore.core.config.enums.Field.CATEGORY
    )

    companion object {

        @JvmStatic
        fun provideFieldAndExpectedName() = arrayOf(
            Arguments.of(com.example.truckercore.core.config.enums.Field.ID, "id"),
            Arguments.of(com.example.truckercore.core.config.enums.Field.COMPANY_ID, "companyId"),
            Arguments.of(com.example.truckercore.core.config.enums.Field.CATEGORY, "category")
        )

    }

    //----------------------------------------------------------------------------------------------
    // Testing amount entries
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should have the expected collection size`() {
        val collectionSize = com.example.truckercore.core.config.enums.Field.entries.size
        val expectedSize = expectedCollection.size
        assertEquals(expectedSize, collectionSize)
    }

    @Test
    fun `should have the expected entries`() {
        val containsAll = expectedCollection.containsAll(com.example.truckercore.core.config.enums.Field.entries)
        assertTrue(containsAll)
    }

    //----------------------------------------------------------------------------------------------
    // Testing Names
    //----------------------------------------------------------------------------------------------
    @ParameterizedTest
    @MethodSource("provideFieldAndExpectedName")
    fun `should return the expected name`(field: com.example.truckercore.core.config.enums.Field, expectedName: String) {
        assertEquals(field.getName(), expectedName)
    }

}