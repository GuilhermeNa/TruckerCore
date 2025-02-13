/*
package com.example.truckercore.unit.modules.employee.admin.repository

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.repository.AdminRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AdminRepositoryImplTest {

    private lateinit var repository: AdminRepository
    private lateinit var fireBaseRepository: FirebaseRepository<AdminDto>

    @BeforeEach
    fun setup() {
        fireBaseRepository = mockk(relaxed = true)
        repository = AdminRepositoryImpl(fireBaseRepository)
    }

    @Test
    fun `should call fireBase repository create`() = runTest {
        // Arrange
        val dto = TestAdminDataProvider.getBaseDto()

        // Call
        repository.create(dto)

        // Assertion
        coVerify { fireBaseRepository.create(dto) }
    }

    @Test
    fun `should call fireBase repository update`() = runTest {
        // Arrange
        val dto = TestAdminDataProvider.getBaseDto()

        // Call
        repository.update(dto)

        // Assertion
        coVerify { fireBaseRepository.update(dto) }
    }

    @Test
    fun `should call fireBase repository delete`() = runTest {
        // Arrange
        val id = "id"

        // Call
        repository.delete(id)

        // Assertion
        coVerify { fireBaseRepository.delete(id) }
    }

    @Test
    fun `should call fireBase repository exists`() = runTest {
        // Arrange
        val id = "id"

        // Call
        repository.entityExists(id)

        // Assertion
        coVerify { fireBaseRepository.entityExists(id) }
    }

    @Test
    fun `should call fireBase repository simpleDocumentFetch`() = runTest {
        // Arrange
        val id = "id"

        // Call
        repository.fetchById(id)

        // Assertion
        coVerify { fireBaseRepository.documentFetch(id) }
    }

}*/
