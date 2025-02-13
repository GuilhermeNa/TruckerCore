package com.example.truckercore.unit.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.util.FirebaseConverter
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FirebaseConverterTest {

    private val converterSpy: FirebaseConverter = spyk(recordPrivateCalls = true)
    private val docSnap: DocumentSnapshot = mockk(relaxed = true)
    private val querySnap: QuerySnapshot = mockk(relaxed = true)
    private val clazz = User::class.java

    @Test
    fun `processQuerySnapShot() should return success when snapShot is populated and class is valid`() {
        // Arrange
        val expectedDtoList = mockk<List<PersonalDataDto>>()
        val expectedResult = Response.Success(expectedDtoList)

        every { querySnap.isEmpty } returns false
        every { converterSpy["convertToList"](querySnap, clazz) } returns expectedDtoList

        // Call
        val result = converterSpy.processQuerySnapShot(querySnap, clazz)

        // Assertions
        assertEquals(expectedResult, result)
        verifyOrder {
            querySnap.isEmpty
            converterSpy["convertToList"](querySnap, clazz)
        }
    }

    @Test
    fun `processQuerySnapShot() should return empty when the query is empty`() {
        // Objects
        val expectedResult = Response.Empty

        every { querySnap.isEmpty } returns true

        // Call
        val result = converterSpy.processQuerySnapShot(querySnap, clazz)

        // Assertions
        assertEquals(expectedResult, result)
        verify { querySnap.isEmpty }
    }

    @Test
    fun `processDocumentSnapShot() should return success when document exists`() {
        // Objects
        val expectedEntity = mockk<PersonalDataDto>()
        val expectedResult = Response.Success(expectedEntity)

        every { docSnap.exists() } returns true
        every { converterSpy["convertObject"](docSnap, clazz) } returns expectedEntity

        // Call
        val result = converterSpy.processDocumentSnapShot(docSnap, clazz)

        // Assertions
        assertEquals(expectedResult, result)
        verifyOrder {
            docSnap.exists()
            converterSpy["convertObject"](docSnap, clazz)
        }
    }

    @Test
    fun `processDocumentSnapShot() should return empty when document does not exist`() {
        // Objects
        val expectedResult = Response.Empty

        // Behavior
        every { docSnap.exists() } returns false

        // Call
        val result = converterSpy.processDocumentSnapShot(docSnap, clazz)

        // Assertions
        assertEquals(expectedResult, result)
        verify { docSnap.exists() }
    }

}