package com.example.truckercore.unit.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseConverterImpl
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.sealeds.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirebaseConverterImplTest {

    private lateinit var converter: FirebaseConverterImpl<PersonalDataDto>
    private lateinit var documentSnapShot: DocumentSnapshot
    private lateinit var querySnapShot: QuerySnapshot

    @BeforeEach
    fun setup() {
        converter = FirebaseConverterImpl(PersonalDataDto::class.java)
        documentSnapShot = mockk()
        querySnapShot = mockk(relaxed = true)
    }

    @Test
    fun `processQuerySnapShot() should return data when query is not empty`() {
        // Objects
        val data = mockk<List<PersonalDataDto>>()
        val response = Response.Success(data)
        val spy = spyk(converter, recordPrivateCalls = true)

        // Behavior
        every { querySnapShot.isEmpty } returns false
        every { spy["convertToList"](querySnapShot) } returns data

        // Call
        val result = spy.processQuerySnapShot(querySnapShot)

        // Assertions
        assertEquals(response, result)
        verifyOrder {
            querySnapShot.isEmpty
            spy["convertToList"](querySnapShot)
        }
    }

    @Test
    fun `processQuerySnapShot() should return empty when the query is empty`() {
        // Objects
        val response = Response.Empty

        // Behavior
        every { querySnapShot.isEmpty } returns true

        // Call
        val result = converter.processQuerySnapShot(querySnapShot)

        // Assertions
        assertEquals(response, result)
        verify { querySnapShot.isEmpty }
    }

    @Test
    fun `processDocumentSnapShot() should return data when document exists`() {
        // Objects
        val data = mockk<PersonalDataDto>()
        val response = Response.Success(data)
        val spy = spyk(converter, recordPrivateCalls = true)

        // Behavior
        every { documentSnapShot.exists() } returns true
        every { spy["convertObject"](documentSnapShot) } returns data

        // Call
        val result = spy.processDocumentSnapShot(documentSnapShot)

        // Assertions
        assertEquals(response, result)
        verifyOrder {
            documentSnapShot.exists()
            spy["convertObject"](documentSnapShot)
        }
    }

    @Test
    fun `processDocumentSnapShot() should return empty when document does not exist`() {
        // Objects
        val response = Response.Empty

        // Behavior
        every { documentSnapShot.exists() } returns false

        // Call
        val result = converter.processDocumentSnapShot(documentSnapShot)

        // Assertions
        assertEquals(response, result)
        verify { documentSnapShot.exists() }
    }

    @Test
    fun `processEntityExistence() should return true when document exists`() {
        // Objects
        val response = Response.Success(true)

        // Behavior
        every { documentSnapShot.exists() } returns true

        // Call
        val result = converter.processEntityExistence(documentSnapShot)

        // Assertions
        assertEquals(response, result)
        verify { documentSnapShot.exists() }
    }

    @Test
    fun `processEntityExistence() should return false response when document does not exist`() {
        // Objects
        val response = Response.Success(false)

        // Behavior
        every { documentSnapShot.exists() } returns false

        // Call
        val result = converter.processEntityExistence(documentSnapShot)

        // Assertions
        assertEquals(response, result)
        verify { documentSnapShot.exists() }
    }

}