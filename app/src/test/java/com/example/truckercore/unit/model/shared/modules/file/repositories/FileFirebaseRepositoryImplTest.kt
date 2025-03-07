package com.example.truckercore.unit.model.shared.modules.file.repositories

import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.shared.modules.file.dto.FileDto
import com.example.truckercore.shared.modules.file.repository.FileRepository
import com.example.truckercore.shared.modules.file.repository.FileRepositoryImpl
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

internal class FileFirebaseRepositoryImplTest : KoinTest {

    private val fireBaseRepository: FirebaseRepository by inject()
    private val repository: FileRepository by inject()

    private val collection = Collection.FILE
    private val dto = TestFileDataProvider.getBaseDto()
    private val id = "testId"

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<FirebaseRepository> { mockk(relaxed = true) }
                        single<FileRepository> {
                            FileRepositoryImpl(get(), collection = Collection.FILE)
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
        verify { fireBaseRepository.update(collection, dto) }
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
        val request = mockk<FirebaseRequest<FileDto>>()
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
        val request = mockk<FirebaseRequest<FileDto>>()
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
