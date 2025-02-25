package com.example.truckercore.unit.shared.utils.parameters

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.search_params.IllegalDocumentParametersException
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DocumentParametersTest {

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

        // Call
        val documentParameters = DocumentParameters.create(user)
            .setId(id)
            .setStream(true)
            .build()

        // Assertions
        assertNotNull(documentParameters)
        assertEquals(id, documentParameters.id)
        assertTrue(documentParameters.shouldStream)
        assertEquals(user, documentParameters.user)
    }

    @Test
    fun `should throw IllegalDocumentParametersException when id is not provided`() {
        assertThrows<IllegalDocumentParametersException> {
            DocumentParameters.create(user)
                .setStream(true)
                .build()
        }
    }

    @Test
    fun `should throw IllegalDocumentParametersException when provided id is blank`() {
        assertThrows<IllegalDocumentParametersException> {
            DocumentParameters.create(user)
                .setId("")
                .build()
        }
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
