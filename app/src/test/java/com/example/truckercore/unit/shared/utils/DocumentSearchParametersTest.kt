package com.example.truckercore.unit.shared.utils

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue

class DocumentSearchParametersTest {

    private val user: User = mockk()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun `should create DocumentParameters successfully when id and liveObserver are provided`() {
        // Arrange
        val id = "12345"
        val liveObserver = true

        // Call
        val documentParameters = DocumentParameters.create(user)
            .setId(id)
            .setStream(liveObserver)
            .build()

        // Assertions
        assertNotNull(documentParameters)
        assertEquals(id, documentParameters.id)
        assertEquals(liveObserver, documentParameters.shouldStream)
        assertEquals(user, documentParameters.user)
    }

    @Test
    fun `should throw IllegalArgumentException when id is not provided`() {
        // Arrange
        val liveObserver = true

        // Call
        val exception = assertThrows<IllegalArgumentException> {
            DocumentParameters.create(user)
                .setStream(liveObserver)
                .build()
        }

        // Assertions
        assertEquals(
            DocumentParameters.BLANK_ID_ERROR_MESSAGE,
            exception.message
        )
    }

    @Test
    fun `should throw IllegalArgumentException when provided id is blank`() {
        // Call
        val exception = assertThrows<IllegalArgumentException> {
            DocumentParameters.create(user)
                .setId("")
                .build()
        }

        // Assertions
        assertEquals(
            DocumentParameters.BLANK_ID_ERROR_MESSAGE,
            exception.message
        )
    }

    @Test
    fun `should allow liveObserver to be true`() {
        // Call
        val documentParameters = DocumentParameters.create(user)
            .setId("123")
            .setStream(true)
            .build()

        // Assertions
        assertTrue(documentParameters.shouldStream)
    }

    @Test
    fun `should allow liveObserver to be false`() {
        // Call
        val documentParameters = DocumentParameters.create(user)
            .setId("123")
            .setStream(false)
            .build()

        // Assertions
        assertFalse(documentParameters.shouldStream)
    }

    @Test
    fun `should create DocumentParameters with default liveObserver as false`() {
        // Call
        val documentParameters = DocumentParameters.create(user)
            .setId("123")
            .build()

        // Assertions
        assertFalse(documentParameters.shouldStream)
    }

}
