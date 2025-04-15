package com.example.truckercore.unit.model.modules.notification.repository

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.notification.dto.NotificationDto
import com.example.truckercore.model.modules.notification.repository.NotificationRepository
import com.example.truckercore.model.modules.notification.repository.NotificationRepositoryImpl
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class NotificationRepositoryTest : KoinTest {

    private val firebaseRepository: FirebaseRepository by inject()
    private val repository: NotificationRepository by inject()
    private val collection = Collection.NOTIFICATION

    @BeforeEach
    fun beforeEach() {
        startKoin {
            modules(
                module {
                    single<FirebaseRepository> { mockk() }
                    single<NotificationRepository> {
                        NotificationRepositoryImpl(get(), collection = Collection.NOTIFICATION)
                    }
                }
            )
        }
    }

    @AfterEach
    fun afterEach() = stopKoin()

    @Test
    fun `should call create from firebase`() = runTest {
        // Arrange
        val dto: NotificationDto = mockk()
        val response = Response.Success("createdId")

        every { firebaseRepository.create(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.create(dto).first()

        // Assertion
        assertTrue(result is Response.Success)
        assertEquals(result.data, "createdId")
        verify(exactly = 1) { firebaseRepository.create(collection, dto) }
    }

    @Test
    fun `should call update from firebase`() = runTest {
        // Arrange
        val dto: NotificationDto = mockk()
        val response = Response.Success(Unit)

        every { firebaseRepository.update(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.update(dto).first()

        // Assertion
        assertTrue(result is Response.Success)
        assertEquals(result.data, Unit)
        verify(exactly = 1) { firebaseRepository.update(collection, dto) }
    }

    @Test
    fun `should call delete from firebase`() = runTest {
        // Arrange
        val id = "id"
        val response = Response.Success(Unit)

        every { firebaseRepository.delete(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.delete(id).first()

        // Assertion
        assertTrue(result is Response.Success)
        assertEquals(result.data, Unit)
        verify(exactly = 1) { firebaseRepository.delete(collection, id) }
    }

    @Test
    fun `should call entityExists from firebase`() = runTest {
        // Arrange
        val id = "id"
        val response = Response.Success(Unit)

        every { firebaseRepository.entityExists(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.entityExists(id).first()

        // Assertion
        assertTrue(result is Response.Success)
        assertEquals(result.data, Unit)
        verify(exactly = 1) { firebaseRepository.entityExists(collection, id) }
    }

    @Test
    fun `should call fetchByDocument from firebase`() = runTest {
        // Arrange
        val docParams: DocumentParameters = mockk()
        val dto: NotificationDto = mockk()
        val response = Response.Success(dto)

        every {
            firebaseRepository.documentFetch(any() as FirebaseRequest<NotificationDto>)
        } returns flowOf(response)

        // Act
        val result = repository.fetchByDocument(docParams).first()

        // Assertion
        assertTrue(result is Response.Success)
        assertEquals(result.data, dto)
        verify(exactly = 1) {
            firebaseRepository.documentFetch(any() as FirebaseRequest<NotificationDto>)
        }
    }

    @Test
    fun `should call fetchByQuery from firebase`() = runTest {
        // Arrange
        val queryParams: QueryParameters = mockk()
        val dtoList: List<NotificationDto> = mockk()
        val response = Response.Success(dtoList)

        every {
            firebaseRepository.queryFetch(any() as FirebaseRequest<NotificationDto>)
        } returns flowOf(response)

        // Act
        val result = repository.fetchByQuery(queryParams).first()

        // Assertion
        assertTrue(result is Response.Success)
        assertEquals(result.data, dtoList)
        verify(exactly = 1) {
            firebaseRepository.queryFetch(any() as FirebaseRequest<NotificationDto>)
        }
    }

}