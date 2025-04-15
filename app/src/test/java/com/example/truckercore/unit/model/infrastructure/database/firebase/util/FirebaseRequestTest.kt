package com.example.truckercore.unit.model.infrastructure.database.firebase.util

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.FirebaseRequestException
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.SearchParameters
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FirebaseRequestTest {

    private val validCollection = Collection.USER

    private val searchParams = mockk<SearchParameters>()
    private val documentParams = mockk<DocumentParameters>()
    private val queryParams = mockk<QueryParameters>()
    private val validClass = UserDto::class.java

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun `should create FirebaseRequest successfully when valid parameters are provided`() {
        // Call
        val request = FirebaseRequest.create(validClass)
            .setCollection(validCollection)
            .setParams(searchParams)
            .build()

        // Assertions
        assertNotNull(request)
        assertEquals(validCollection, request.collection)
        assertEquals(validClass, request.clazz)
        assertEquals(searchParams, request.params)
    }

    @Test
    fun `should throw FirebaseRequestException when collection is missing`() {
        // Call
        assertThrows<FirebaseRequestException> {
            FirebaseRequest.create(validClass)
                .setParams(searchParams)
                .build()
        }
    }

    @Test
    fun `should throw FirebaseRequestException when params are missing`() {
        // Call
        assertThrows<FirebaseRequestException> {
            FirebaseRequest.create(validClass)
                .setCollection(validCollection).build()
        }
    }

    @Test
    fun `should throw FirebaseRequestException when getDocumentParams is called with non-DocumentParameters`() {
        // Arrange
        val request = FirebaseRequest.create(validClass)
            .setCollection(validCollection)
            .setParams(queryParams)
            .build()

        // Call & Assert
        assertThrows<FirebaseRequestException> {
            request.getDocumentParams()
        }
    }

    @Test
    fun `should throw FirebaseRequestException when getQueryParams is called with non-QueryParameters`() {
        // Arrange
        val request = FirebaseRequest.create(validClass)
            .setCollection(validCollection)
            .setParams(documentParams)
            .build()
        // Call & Assert
        assertThrows<FirebaseRequestException> {
            request.getQueryParams()
        }
    }

}