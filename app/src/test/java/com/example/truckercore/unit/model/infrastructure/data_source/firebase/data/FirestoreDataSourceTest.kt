package com.example.truckercore.unit.model.infrastructure.data_source.firebase.data

import com.example.truckercore._test_data_provider.TestFirestoreDataProvider
import com.example.truckercore._test_data_provider.TestSpecificationProvider
import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore._test_data_provider.fake_objects.FakeID
import com.example.truckercore._test_utils.mockStaticTask
import com.example.truckercore._test_utils.mockStaticTextUtil
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreDataSource
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreErrorMapper
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreSpecInterpreter
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InterpreterException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.MappingException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.UnknownException
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.exceptions.SpecificationException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class FirestoreDataSourceTest : KoinTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val interpreter: FirestoreSpecInterpreter by inject()
    private val dataSource: FirestoreDataSource by inject()

    private val fsProvider = TestFirestoreDataProvider()
    private val specProvider = TestSpecificationProvider()

    @BeforeEach
    fun setup() {
        mockStaticTask()
        mockStaticTextUtil()
        startKoin {
            modules(
                module {
                    single<FirestoreSpecInterpreter> { mockk(relaxed = true) }
                    single { FirestoreErrorMapper() }
                    single { FirestoreDataSource(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing findById
    //----------------------------------------------------------------------------------------------
    @Test
    fun `findById should return DTO when document exists`() = runTest {
        // Arrange
        val spec = specProvider.speckMock(id = FakeID())
        val dto = FakeDto(id = "1")

        val docSnap = fsProvider.docSnapMock(converter = spec.dtoClass, fakeDto = dto)
        val task = fsProvider.taskMock(res = docSnap)
        val docRef = fsProvider.docReference(get = task)

        every { interpreter.interpretIdSearch(any()) } returns docRef

        // Act
        val result = dataSource.findById(spec)

        // Assert
        assertEquals(dto, result)
    }

    @Test
    fun `findById should throw UnknownException when an unmapped error occur`() = runTest {
        // Arrange
        val spec = specProvider.speckMock(id = FakeID())

        every { interpreter.interpretIdSearch(any()) } throws NullPointerException()

        // Act && Assert
        val exception = assertThrows<UnknownException> {
            dataSource.findById(spec)
        }

        assertTrue(exception.cause is NullPointerException)
        assertEquals(
            exception.message,
            "An unexpected error occurred on DataSource. Please check the logs" +
                    " for more details: $spec"
        )
    }

    @Test
    fun `findById should throw InvalidDataException when invalid data is encountered`() = runTest {
        val spec = specProvider.speckMock(id = FakeID())

        every { interpreter.interpretIdSearch(any()) } throws InvalidDataException()

        val exception = assertThrows<InvalidDataException> {
            dataSource.findById(spec)
        }

        assertEquals(
            "An error occurred while attempting to recover valid data. Please verify the" +
                    " data source and ensure that the data format is correct: $spec",
            exception.message
        )
        assertNull(exception.cause)
    }

    @Test
    fun `findById should throw MappingException when mapping fails`() = runTest {
        val spec = specProvider.speckMock(id = FakeID())

        every { interpreter.interpretIdSearch(any()) } throws MappingException()

        val exception = assertThrows<MappingException> {
            dataSource.findById(spec)
        }

        assertEquals(
            "Data mapping failed during the conversion from API response to domain model: $spec.",
            exception.message
        )
        assertNull(exception.cause)
    }

    @Test
    fun `findById should throw InterpreterException when a specification error occurs`() = runTest {
        val spec = specProvider.speckMock(id = FakeID())
        val cause = SpecificationException("spec failure")

        every { interpreter.interpretIdSearch(any()) } throws cause

        val exception = assertThrows<InterpreterException> {
            dataSource.findById(spec)
        }

        assertEquals(
            "An error occurred while interpreting the: $spec",
            exception.message
        )
        assertEquals(cause, exception.cause)
    }

    @Test
    fun `findById should throw NetworkException when a network error occurs`() = runTest {
        val spec = specProvider.speckMock(id = FakeID())
        val cause = FirebaseNetworkException("Fake exception")

        every { interpreter.interpretIdSearch(any()) } throws cause

        val exception = assertThrows<NetworkException> {
            dataSource.findById(spec)
        }

        assertEquals(
            "Network connectivity issue encountered while interacting with DataSource: $spec",
            exception.message
        )
        assertEquals(cause, exception.cause)
    }

    @Test
    fun `findById should throw UnknownException when an unmapped error occurs`() = runTest {
        val spec = specProvider.speckMock(id = FakeID())

        every { interpreter.interpretIdSearch(any()) } throws NullPointerException()

        val exception = assertThrows<UnknownException> {
            dataSource.findById(spec)
        }

        assertEquals(
            "An unexpected error occurred on DataSource. Please check the logs for more details: $spec",
            exception.message
        )
        assertTrue(exception.cause is NullPointerException)
    }

    //----------------------------------------------------------------------------------------------
    // Testing findAllBy
    //----------------------------------------------------------------------------------------------
    @Test
    fun `findAllBy should return list of DTOs when documents exist`() = runTest {
        // Arrange
        val spec = specProvider.speckMock()

        val doc1 = fsProvider.queryDocSnapMock(converter = spec.dtoClass, FakeDto("1"))
        val doc2 = fsProvider.queryDocSnapMock(converter = spec.dtoClass, FakeDto("2"))
        val querySnap = fsProvider.querySnapMock(docs = listOf(doc1, doc2))
        val task = fsProvider.taskMock(res = querySnap)
        val query = fsProvider.query(get = task)

        every { interpreter.interpretFilterSearch(any()) } returns query

        // Act
        val result = dataSource.findAllBy(spec)

        // Assert
        assertEquals(listOf(FakeDto("1"), FakeDto("2")), result)
    }

    @Test
    fun `findAllBy should throw UnknownException when an unmapped error occurs`() = runTest {
        // Arrange
        val spec = specProvider.speckMock()

        every { interpreter.interpretFilterSearch(any()) } throws NullPointerException()

        // Act && Assert
        val exception = assertThrows<UnknownException> {
            dataSource.findAllBy(spec)
        }

        assertTrue(exception.cause is NullPointerException)
        assertEquals(
            "An unexpected error occurred on DataSource. Please check the logs for more details: $spec",
            exception.message
        )
    }

    @Test
    fun `findAllBy should throw InvalidDataException when invalid data is encountered`() = runTest {
        val spec = specProvider.speckMock()

        every { interpreter.interpretFilterSearch(any()) } throws InvalidDataException()

        val exception = assertThrows<InvalidDataException> {
            dataSource.findAllBy(spec)
        }

        assertEquals(
            "An error occurred while attempting to recover valid data. Please verify the" +
                    " data source and ensure that the data format is correct: $spec",
            exception.message
        )
        assertNull(exception.cause)
    }

    @Test
    fun `findAllBy should throw MappingException when mapping fails`() = runTest {
        val spec = specProvider.speckMock()

        every { interpreter.interpretFilterSearch(any()) } throws MappingException()

        val exception = assertThrows<MappingException> {
            dataSource.findAllBy(spec)
        }

        assertEquals(
            "Data mapping failed during the conversion from API response to domain model: $spec.",
            exception.message
        )
        assertNull(exception.cause)
    }

    @Test
    fun `findAllBy should throw InterpreterException when a specification error occurs`() =
        runTest {
            val spec = specProvider.speckMock()
            val cause = SpecificationException("spec failure")

            every { interpreter.interpretFilterSearch(any()) } throws cause

            val exception = assertThrows<InterpreterException> {
                dataSource.findAllBy(spec)
            }

            assertEquals(
                "An error occurred while interpreting the: $spec",
                exception.message
            )
            assertEquals(cause, exception.cause)
        }

    @Test
    fun `findAllBy should throw NetworkException when a network error occurs`() = runTest {
        val spec = specProvider.speckMock()
        val cause = FirebaseNetworkException("Fake exception")

        every { interpreter.interpretFilterSearch(any()) } throws cause

        val exception = assertThrows<NetworkException> {
            dataSource.findAllBy(spec)
        }

        assertEquals(
            "Network connectivity issue encountered while interacting with DataSource: $spec",
            exception.message
        )
        assertEquals(cause, exception.cause)
    }

    //----------------------------------------------------------------------------------------------
    // Testing flowOneBy
    //----------------------------------------------------------------------------------------------
    @Test
    fun `flowOneBy should return a flow DTO when document exists`() = runTest {
        // Arrange
        val spec = specProvider.speckMock(id = FakeID())
        val dto = FakeDto(id = "1")

        val docSnap = fsProvider.docSnapMock(converter = spec.dtoClass, fakeDto = dto)
        val task = fsProvider.taskMock(res = docSnap)
        val docRef = fsProvider.docReference(get = task)
        val listenerRegistration: ListenerRegistration = mockk(relaxed = true)

        every { interpreter.interpretIdSearch(any()) } returns docRef
        every { docRef.addSnapshotListener(any()) } answers {
            val listener = it.invocation.args[0] as EventListener<DocumentSnapshot>
            listener.onEvent(docSnap, null)
            listenerRegistration
        }

        // Act
        val result = dataSource.flowOneBy(spec).first()

        // Arrange
        assertEquals(FakeDto("1"), result)
    }

    @Test
    fun `flowOneBy should throw InterpreterException when a specification error occurs`() =
        runTest {
            // Arrange
            val spec = specProvider.speckMock()
            val cause = SpecificationException("spec failure")

            every { interpreter.interpretIdSearch(any()) } throws cause

            // Act
            assertThrows<InterpreterException> {
                dataSource.flowOneBy(spec).single()
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing flowAllBy
    //----------------------------------------------------------------------------------------------
    @Test
    fun `flowAllBy should return a flow DTO when document exists`() = runTest {
        // Arrange
        val spec = specProvider.speckMock()

        val doc1 = fsProvider.queryDocSnapMock(converter = spec.dtoClass, FakeDto("1"))
        val doc2 = fsProvider.queryDocSnapMock(converter = spec.dtoClass, FakeDto("2"))
        val querySnap = fsProvider.querySnapMock(docs = listOf(doc1, doc2))
        val task = fsProvider.taskMock(res = querySnap)
        val query = fsProvider.query(get = task)

        every { interpreter.interpretFilterSearch(any()) } returns query

        val listenerRegistration: ListenerRegistration = mockk(relaxed = true)

        every { interpreter.interpretFilterSearch(any()) } returns query
        every { query.addSnapshotListener(any()) } answers {
            val listener = it.invocation.args[0] as EventListener<QuerySnapshot>
            listener.onEvent(querySnap, null)
            listenerRegistration
        }

        // Act
        val result = dataSource.flowAllBy(spec).first()

        // Arrange
        assertEquals(result?.get(0), FakeDto("1"))
        assertEquals(result?.get(1), FakeDto("2"))
    }

}