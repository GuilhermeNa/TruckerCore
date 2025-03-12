package com.example.truckercore.unit.model.modules.vip.repository

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.vip.dto.VipDto
import com.example.truckercore.model.modules.vip.repository.VipRepository
import com.example.truckercore.model.modules.vip.repository.VipRepositoryImpl
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
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

internal class VipRepositoryTest : KoinTest {

    private val fbRepository: FirebaseRepository by inject()
    private val repository: VipRepository by inject()

    @BeforeEach
    fun beforeEach() {
        startKoin {
            modules(
                module {
                    single<FirebaseRepository> { mockk() }
                    single<VipRepository> { VipRepositoryImpl(get(), Collection.VIP) }
                }
            )
        }
    }

    @AfterEach
    fun afterEach() = stopKoin()

    @Test
    fun `should call create from firebaseRepository`() = runTest {
        // Arrange
        val dto: VipDto = mockk()
        val response: Response<String> = mockk()
        every { fbRepository.create(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.create(dto).single()

        // Call
        assertEquals(response, result)
        verify(exactly = 1) { fbRepository.create(Collection.VIP, dto) }
    }

    @Test
    fun `should call update from firebaseRepository`() = runTest {
        // Arrange
        val dto: VipDto = mockk()
        val response: Response<Unit> = mockk()
        every { fbRepository.update(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.update(dto).single()

        // Call
        assertEquals(response, result)
        verify(exactly = 1) { fbRepository.update(Collection.VIP, dto) }
    }

    @Test
    fun `should call delete from firebaseRepository`() = runTest {
        // Arrange
        val id = "id"
        val response: Response<Unit> = mockk()
        every { fbRepository.delete(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.delete(id).single()

        // Call
        assertEquals(response, result)
        verify(exactly = 1) { fbRepository.delete(Collection.VIP, id) }
    }

    @Test
    fun `should call entityExists from firebaseRepository`() = runTest {
        // Arrange
        val id = "id"
        val response: Response<Unit> = mockk()
        every { fbRepository.entityExists(any(), any()) } returns flowOf(response)

        // Act
        val result = repository.entityExists(id).single()

        // Call
        assertEquals(response, result)
        verify(exactly = 1) { fbRepository.entityExists(Collection.VIP, id) }
    }

    @Test
    fun `should call documentFetch from firebaseRepository`() = runTest {
        // Arrange
        val params: DocumentParameters = mockk()
        val response: Response<VipDto> = mockk()
        every {
            fbRepository.documentFetch(any() as FirebaseRequest<VipDto>)
        } returns flowOf(response)

        // Act
        val result = repository.fetchByDocument(params).single()

        // Call
        assertEquals(response, result)
        verify(exactly = 1) { fbRepository.documentFetch(any() as FirebaseRequest<VipDto>) }
    }

    @Test
    fun `should call queryFetch from firebaseRepository`() = runTest {
        // Arrange
        val params: QueryParameters = mockk()
        val response: Response<List<VipDto>> = mockk()
        every {
            fbRepository.queryFetch(any() as FirebaseRequest<VipDto>)
        } returns flowOf(response)

        // Act
        val result = repository.fetchByQuery(params).single()

        // Call
        assertEquals(response, result)
        verify(exactly = 1) { fbRepository.queryFetch(any() as FirebaseRequest<VipDto>) }
    }

}