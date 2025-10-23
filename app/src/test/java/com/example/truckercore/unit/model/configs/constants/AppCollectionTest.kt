package com.example.truckercore.unit.model.configs.constants

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AppCollectionTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val expectedCollection = listOf(
        com.example.truckercore.core.config.collections.AppCollection.FAKE,
        com.example.truckercore.core.config.collections.AppCollection.USER,
        com.example.truckercore.core.config.collections.AppCollection.ADMIN,
        com.example.truckercore.core.config.collections.AppCollection.COMPANY
    )

    //----------------------------------------------------------------------------------------------
    // Testing amount entries
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should have the expected collection size`() {
        val collectionSize = com.example.truckercore.core.config.collections.AppCollection.entries.size
        val expectedSize = expectedCollection.size
        assertEquals(expectedSize, collectionSize)
    }

    @Test
    fun `should have the expected entries`() {
        val containsAll = expectedCollection.containsAll(com.example.truckercore.core.config.collections.AppCollection.entries)
        assertTrue(containsAll)
    }

}