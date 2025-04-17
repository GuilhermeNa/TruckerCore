package com.example.truckercore.unit.model.app_constants

import com.example.truckercore.model.configs.constants.Parent
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ParentTest {

    private val expectedParents = hashSetOf(
        Parent.TRUCK, Parent.TRAILER, Parent.DRIVER, Parent.ADMIN
    )

    @Test
    fun `enum should have the expected enums`() {
        Parent.entries.forEach { enum ->
            assertTrue(expectedParents.contains(enum))
        }
    }

    @Test
    fun `enum should have the expected size`() {
        val expectedSize = expectedParents.size
        val actualSize = Parent.entries.size
        assertEquals(expectedSize, actualSize)
    }

}