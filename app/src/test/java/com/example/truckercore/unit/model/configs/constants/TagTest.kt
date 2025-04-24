package com.example.truckercore.unit.model.configs.constants

import com.example.truckercore.model.configs.constants.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TagTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val expectedCollection = listOf(
        Tag.DEBUG,
        Tag.ERROR,
        Tag.INFO,
        Tag.WARN
    )

    //----------------------------------------------------------------------------------------------
    // Testing amount entries
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should have the expected collection size`() {
        val collectionSize = Tag.entries.size
        val expectedSize = expectedCollection.size
        assertEquals(expectedSize, collectionSize)
    }

    @Test
    fun `should have the expected entries`() {
        val containsAll = Tag.entries.containsAll(expectedCollection)
        assertTrue(containsAll)
    }

    //----------------------------------------------------------------------------------------------
    // Testing Names
    //----------------------------------------------------------------------------------------------
    @Test
    fun `DEBUG should return the correct name`() {
        assertEquals("tag_debug", Tag.DEBUG.getName())
    }

    @Test
    fun `ERROR should return the correct name`() {
        assertEquals("tag_error", Tag.ERROR.getName())
    }

    @Test
    fun `INFO should return the correct name`() {
        assertEquals("tag_info", Tag.INFO.getName())
    }

    @Test
    fun `WARN should return the correct name`() {
        assertEquals("tag_warn", Tag.WARN.getName())
    }

}