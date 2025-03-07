package com.example.truckercore.unit.model.modules.person.employee.admin.repository

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.modules.person.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.person.employee.admin.repository.AdminRepositoryImpl
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
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

internal class AdminRepositoryImplTest : KoinTest {

    private val fireBaseRepository: FirebaseRepository by inject()
    private val repository: AdminRepository by inject()

    private val collection = Collection.ADMIN
    private val dto = TestAdminDataProvider.getBaseDto()
    private val id = "testId"

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<FirebaseRepository> { mockk(relaxed = true) }
                        single<AdminRepository> {
                            AdminRepositoryImpl(get(), collection = Collection.ADMIN)
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
        verify { fireBaseRepository.delete(collection, id) }
    }

    @Test
    fun `should call fireBase repository exists`() {
        // Call
        repository.entityExists(id)

        // Assertion
        verify { fireBaseRepository.entityExists(collection, id) }
    }

    @Test
    fun `fetchByDocument() should call fireBaseRepository`() {
        // Arrange
        val params = mockk<DocumentParameters>()
        val request = mockk<FirebaseRequest<AdminDto>>()
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
        val request = mockk<FirebaseRequest<AdminDto>>()
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
