package com.example.truckercore.unit.model.configs.constants

import com.example.truckercore.model.configs.constants.Collection
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CollectionTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val expectedCollection = listOf(
        Collection.FAKE,
        Collection.USER,
        Collection.ADMIN,
        Collection.COMPANY
    )

    //----------------------------------------------------------------------------------------------
    // Testing amount entries
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should have the expected collection size`() {
        val collectionSize = Collection.entries.size
        val expectedSize = expectedCollection.size
        assertEquals(expectedSize, collectionSize)
    }

    @Test
    fun `should have the expected entries`() {
        val containsAll = expectedCollection.containsAll(Collection.entries)
        assertTrue(containsAll)
    }

}