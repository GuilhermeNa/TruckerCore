package com.example.truckercore.unit.model.configs.constants

import com.example.truckercore.core.config.enums.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TagTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val expectedCollection = listOf(
        com.example.truckercore.core.config.enums.Tag.DEBUG,
        com.example.truckercore.core.config.enums.Tag.ERROR,
        com.example.truckercore.core.config.enums.Tag.INFO,
        com.example.truckercore.core.config.enums.Tag.WARN
    )

    //----------------------------------------------------------------------------------------------
    // Testing amount entries
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should have the expected collection size`() {
        val collectionSize = com.example.truckercore.core.config.enums.Tag.entries.size
        val expectedSize = expectedCollection.size
        assertEquals(expectedSize, collectionSize)
    }

    @Test
    fun `should have the expected entries`() {
        val containsAll = com.example.truckercore.core.config.enums.Tag.entries.containsAll(expectedCollection)
        assertTrue(containsAll)
    }

    //----------------------------------------------------------------------------------------------
    // Testing Names
    //----------------------------------------------------------------------------------------------
    @Test
    fun `DEBUG should return the correct name`() {
        assertEquals("tag_debug", com.example.truckercore.core.config.enums.Tag.DEBUG.getName())
    }

    @Test
    fun `ERROR should return the correct name`() {
        assertEquals("tag_error", com.example.truckercore.core.config.enums.Tag.ERROR.getName())
    }

    @Test
    fun `INFO should return the correct name`() {
        assertEquals("tag_info", com.example.truckercore.core.config.enums.Tag.INFO.getName())
    }

    @Test
    fun `WARN should return the correct name`() {
        assertEquals("tag_warn", com.example.truckercore.core.config.enums.Tag.WARN.getName())
    }

}