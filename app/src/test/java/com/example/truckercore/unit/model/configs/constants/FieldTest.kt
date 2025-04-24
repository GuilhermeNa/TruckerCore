package com.example.truckercore.unit.model.configs.constants

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.configs.constants.Field
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

class FieldTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val expectedCollection = listOf(
        Field.ID,
        Field.COMPANY_ID
    )

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
        val containsAll = Field.entries.containsAll(expectedCollection)
        assertTrue(containsAll)
    }

    //----------------------------------------------------------------------------------------------
    // Testing Names
    //----------------------------------------------------------------------------------------------
    @Test
    fun `ID should return the correct name`() {
        assertEquals(Field.ID.getName(), "id")
    }

    @Test
    fun `CATEGORY should return the correct name`() {
        assertEquals(Field.CATEGORY.getName(), "category")
    }

    @Test
    fun `COMPANY_ID should return the correct name`() {
        assertEquals(Field.COMPANY_ID.getName(), "companyId")
    }

}