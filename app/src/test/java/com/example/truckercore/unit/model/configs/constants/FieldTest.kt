package com.example.truckercore.unit.model.configs.constants

import com.example.truckercore.model.configs.enums.Field
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
        Field.ID,
        Field.COMPANY_ID,
        Field.CATEGORY
    )

    companion object {

        @JvmStatic
        fun provideFieldAndExpectedName() = arrayOf(
            Arguments.of(Field.ID, "id"),
            Arguments.of(Field.COMPANY_ID, "companyId"),
            Arguments.of(Field.CATEGORY, "category")
        )

    }

    //----------------------------------------------------------------------------------------------
    // Testing amount entries
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should have the expected collection size`() {
        val collectionSize = Field.entries.size
        val expectedSize = expectedCollection.size
        assertEquals(expectedSize, collectionSize)
    }

    @Test
    fun `should have the expected entries`() {
        val containsAll = expectedCollection.containsAll(Field.entries)
        assertTrue(containsAll)
    }

    //----------------------------------------------------------------------------------------------
    // Testing Names
    //----------------------------------------------------------------------------------------------
    @ParameterizedTest
    @MethodSource("provideFieldAndExpectedName")
    fun `should return the expected name`(field: Field, expectedName: String) {
        assertEquals(field.getName(), expectedName)
    }

}