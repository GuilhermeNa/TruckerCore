package com.example.truckercore.unit.modules.business_central.repository

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepositoryImpl
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class BusinessCentralRepositoryImplTest {

    private val fireBaseRepository: FirebaseRepository = mockk(relaxed = true)
    private val collection = Collection.CENTRAL
    private val repository = BusinessCentralRepositoryImpl(fireBaseRepository, collection)

    private val dto = TestBusinessCentralDataProvider.getBaseDto()
    private val id = "testId"


    @Test
    fun `create() should call fireBaseRepository`() = runTest {
        // Call
        repository.create(dto)

        // Assertions
        verify { fireBaseRepository.create(collection, dto) }
    }

    @Test
    fun `update() should call fireBaseRepository`() = runTest {
        // Call
        repository.update(dto)

        // Assertions
        verify { fireBaseRepository.update(collection, dto) }
    }

    @Test
    fun `delete() should call fireBaseRepository`() = runTest {
        // Call
        repository.delete(id)

        // Assertions
        verify { fireBaseRepository.delete(collection, id) }
    }

    @Test
    fun `entityExists() should call fireBaseRepository`() = runTest {
        // Call
        repository.entityExists(id)

        // Assertions
        verify { fireBaseRepository.entityExists(collection, id) }
    }

    @Test
    fun `fetchByDocument() should call fireBaseRepository`() = runTest {
        // Arrange
        val params = mockk<DocumentParameters>()
        val request = mockk<FirebaseRequest<BusinessCentralDto>>()
        val repositorySpy = spyk(repository, recordPrivateCalls = true)

        every { repositorySpy["createFirestoreRequest"](params) } returns request

        // Call
        repositorySpy.fetchByDocument(params)

        // Assertions
        verify { fireBaseRepository.documentFetch(request) }
    }

    @Test
    fun `fetchByQuery() should call fireBaseRepository`() = runTest {
        // Arrange
        val params = mockk<QueryParameters>()
        val request = mockk<FirebaseRequest<BusinessCentralDto>>()
        val repositorySpy = spyk(repository, recordPrivateCalls = true)

        every { repositorySpy["createFirestoreRequest"](params) } returns request

        // Call
        repositorySpy.fetchByQuery(params)

        // Assertions
        verify { fireBaseRepository.documentFetch(request) }
    }

}