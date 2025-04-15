package com.example.truckercore.unit.model.modules.fleet.trailer.repository

import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepositoryImpl
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class TrailerRepositoryImplTest : KoinTest {

    private val fireBaseRepository: FirebaseRepository by inject()
    private val repository: TrailerRepository by inject()

    private val collection = Collection.TRAILER
    private val dto = TestTrailerDataProvider.getBaseDto()
    private val id = "testId"

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<FirebaseRepository> { mockk(relaxed = true) }
                        single<TrailerRepository> {
                            TrailerRepositoryImpl(get(), collection = Collection.TRAILER)
                        }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `should call fireBase repository create`() {
        // Call
        repository.create(dto)

        // Assertion
        verify { fireBaseRepository.create(collection, dto) }
    }

    @Test
    fun `should call fireBase repository update`() {
        // Call
        repository.update(dto)

        // Assertion
        verify { fireBaseRepository.update(collection, dto) }
    }

    @Test
    fun `should call fireBase repository delete`() {
        // Call
        repository.delete(id)

        // Assertion
        coVerify { fireBaseRepository.delete(collection, id) }
    }

    @Test
    fun `should call fireBase repository exists`() {
        // Call
        repository.entityExists(id)

        // Assertion
        coVerify { fireBaseRepository.entityExists(collection, id) }
    }

    @Test
    fun `fetchByDocument() should call fireBaseRepository`() {
        // Arrange
        val params = mockk<DocumentParameters>()
        val request = mockk<FirebaseRequest<TrailerDto>>()
        val repositorySpy = spyk(repository, recordPrivateCalls = true)

        every { repositorySpy["createFirestoreRequest"](params) } returns request

        // Call
        repositorySpy.fetchByDocument(params)

        // Assertions
        verifyOrder {
            repositorySpy["createFirestoreRequest"](params)
            fireBaseRepository.documentFetch(request)
        }
    }

    @Test
    fun `fetchByQuery() should call fireBaseRepository`() {
        // Arrange
        val params = mockk<QueryParameters>()
        val request = mockk<FirebaseRequest<TrailerDto>>()
        val repositorySpy = spyk(repository, recordPrivateCalls = true)

        every { repositorySpy["createFirestoreRequest"](params) } returns request

        // Call
        repositorySpy.fetchByQuery(params)

        // Assertions
        verifyOrder {
            repositorySpy["createFirestoreRequest"](params)
            fireBaseRepository.queryFetch(request)
        }
    }

}
