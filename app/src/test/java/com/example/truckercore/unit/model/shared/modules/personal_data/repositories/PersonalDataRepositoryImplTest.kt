package com.example.truckercore.unit.model.shared.modules.personal_data.repositories

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepositoryImpl
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

internal class PersonalDataRepositoryImplTest : KoinTest {

    private val fireBaseRepository: FirebaseRepository by inject()
    private val repository: PersonalDataRepository by inject()

    private val collection = Collection.PERSONAL_DATA
    private val dto = TestPersonalDataDataProvider.getBaseDto()
    private val id = "testId"

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<FirebaseRepository> { mockk(relaxed = true) }
                        single<PersonalDataRepository> {
                            PersonalDataRepositoryImpl(get(), collection = Collection.PERSONAL_DATA)
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
    fun `create() should call fireBaseRepository`() {
        // Call
        repository.create(dto)

        // Assertions
        verify { fireBaseRepository.create(collection, dto) }
    }

    @Test
    fun `update() should call fireBaseRepository`() {
        // Call
        repository.update(dto)

        // Assertions
        coVerify { fireBaseRepository.update(collection, dto) }
    }

    @Test
    fun `delete() should call fireBaseRepository`() {
        // Call
        repository.delete(id)

        // Assertions
        verify { fireBaseRepository.delete(collection, id) }
    }

    @Test
    fun `entityExists() should call fireBaseRepository`() {
        // Call
        repository.entityExists(id)

        // Assertions
        verify { fireBaseRepository.entityExists(collection, id) }
    }

    @Test
    fun `fetchByDocument() should call fireBaseRepository`() {
        // Arrange
        val params = mockk<DocumentParameters>()
        val request = mockk<FirebaseRequest<PersonalDataDto>>()
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
        val request = mockk<FirebaseRequest<PersonalDataDto>>()
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
