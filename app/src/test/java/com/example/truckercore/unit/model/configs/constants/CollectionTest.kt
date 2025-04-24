package com.example.truckercore.unit.model.configs.constants

import com.example.truckercore.model.configs.constants.Collection
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CollectionTest {

    private val expectedCollection = listOf(
        Collection.USER,
        Collection.AUDIT
    )

    @Test
    fun `should have the expected collection size`() {
        val collectionSize = Collection.entries.size
        val expectedSize = expectedCollection.size
        assertEquals(expectedSize, collectionSize)
    }

    @Test
    fun `should have the expected entries`() {
        val containsAll = Collection.entries.containsAll(expectedCollection)
        assertTrue(containsAll)
    }

}