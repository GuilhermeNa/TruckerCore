package com.example.truckercore.unit.model.infrastructure.data_source.firebase.data

import com.example.truckercore._test_data_provider.TestDtoProvider
import com.example.truckercore._test_data_provider.TestFirestoreDataProvider
import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore.model.infrastructure.data_source.firebase.data.safeEmit
import com.example.truckercore.model.infrastructure.data_source.firebase.data.safeInterpretOrEmit
import com.example.truckercore.model.infrastructure.data_source.firebase.data.toDto
import com.example.truckercore.model.infrastructure.data_source.firebase.data.toList
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.MappingException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.UnknownException
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FirestoreDataExpressionsTest {

    //----------------------------------------------------------------------------------------------
    // Testing safeInterpretOrEmit
    //----------------------------------------------------------------------------------------------
    private val provider = TestFirestoreDataProvider()
    private val dtoProvider = TestDtoProvider()

    //----------------------------------------------------------------------------------------------
    // Testing safeInterpretOrEmit
    //----------------------------------------------------------------------------------------------
    @Test
    fun `safeInterpretOrEmit should return result when block succeeds`() = runTest {
        val flow = callbackFlow {
            val result = safeInterpretOrEmit(
                block = { "Success" },
                error = { UnknownException() }
            )
            trySend(result)
            awaitClose()
        }
        val result = flow.first()
        assertEquals("Success", result)
    }

    @Test
    fun `safeInterpretOrEmit should close flow with error when block throws`() = runTest {
        val flow = callbackFlow<Unit> {
            safeInterpretOrEmit(
                block = { throw NullPointerException() },
                error = { UnknownException() }
            )
        }
        assertThrows<UnknownException> { flow.first() }
    }

    //----------------------------------------------------------------------------------------------
    // Testing safeEmit
    //----------------------------------------------------------------------------------------------
    @Test
    fun `safeEmit should trySend result when block succeeds`() = runTest {
        val flow = callbackFlow {
            safeEmit(
                block = { "Success" },
                error = { UnknownException() }
            )
            awaitClose()
        }
        val result = flow.first()
        assertEquals("Success", result)
    }

    @Test
    fun `safeEmit should close flow with error when block throws`() = runTest {
        val flow = callbackFlow<Unit> {
            safeEmit(
                block = { throw NullPointerException() },
                error = { UnknownException() }
            )
            awaitClose()
        }
        assertThrows<UnknownException> { flow.first() }
    }

    //----------------------------------------------------------------------------------------------
    // Testing toDto
    //----------------------------------------------------------------------------------------------
    @Test
    fun `toDto should return null when document does not exist`() {
        val docSnap = provider.docSnapMock(exists = false)

        val result = docSnap.toDto(FakeDto::class.java)

        assertNull(result)
    }

    @Test
    fun `toDto should throw InvalidDataException when snapshot is null`() {
        val docSnap: DocumentSnapshot? = null

        assertThrows<InvalidDataException> {
            docSnap.toDto(FakeDto::class.java)
        }
    }

    @Test
    fun `toDto should throw MappingException when toObject returns null`() {
        val docSnap = provider.docSnapMock(fakeDto = null)

        assertThrows<MappingException> {
            docSnap.toDto(FakeDto::class.java)
        }
    }

    @Test
    fun `toDto should return DTO when document is valid and maps correctly`() {
        val expectedDto = dtoProvider.fakeDto()
        val docSnap = provider.docSnapMock(fakeDto = expectedDto)

        val result = docSnap.toDto(FakeDto::class.java)

        assertEquals(expectedDto, result)
    }

    //----------------------------------------------------------------------------------------------
    // Testing toList
    //----------------------------------------------------------------------------------------------
    @Test
    fun `toList should return null when snapshot is empty`() {
        val snapshot = provider.querySnapMock(isEmpty = true)

        val result = snapshot.toList(FakeDto::class.java)

        assertNull(result)
    }

    @Test
    fun `toList should return list of DTOs when snapshot is valid`() {
        val doc1 = provider.queryDocSnapMock(fakeDto = dtoProvider.fakeDto("1"))
        val doc2 = provider.queryDocSnapMock(fakeDto = dtoProvider.fakeDto("2"))

        val snapshot = provider.querySnapMock(docs = listOf(doc1, doc2))

        val result = snapshot.toList(FakeDto::class.java)

        assertEquals(result?.get(0)?.id, "1")
        assertEquals(result?.get(1)?.id, "2")
    }

}