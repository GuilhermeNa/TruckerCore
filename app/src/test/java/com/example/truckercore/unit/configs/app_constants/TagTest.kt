package com.example.truckercore.unit.configs.app_constants

import com.example.truckercore.configs.app_constants.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TagTest {

    @Test
    fun `test getName should return correct tag name`() {
        assertEquals("tag_debug", Tag.DEBUG.getName())
        assertEquals("tag_error", Tag.ERROR.getName())
        assertEquals("tag_warn", Tag.WARN.getName())
    }

}