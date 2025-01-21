package com.example.truckercore.unit.modules.employee.driver.repository

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.repository.DriverRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DriverRepositoryImplTest {

    private lateinit var fireBaseRepository: FirebaseRepository<DriverDto>
    private lateinit var repository: DriverRepository
    private lateinit var dto: DriverDto
    private lateinit var id: String

    @BeforeEach
    fun setup() {
        fireBaseRepository = mockk(relaxed = true)
        repository = DriverRepositoryImpl(fireBaseRepository)
        dto = TestDriverDataProvider.getBaseDto()
        id = "testId"
    }

    @Test
    fun `create() should call fireBaseRepository`() = runTest {
        // Call
        repository.create(dto)

        // Assertions
        coVerify { fireBaseRepository.create(dto) }
    }

    @Test
    fun `update() should call fireBaseRepository`() = runTest {
        // Call
        repository.update(dto)

        // Assertions
        coVerify { fireBaseRepository.update(dto) }
    }

    @Test
    fun `delete() should call fireBaseRepository`() = runTest {
        // Call
        repository.delete(id)

        // Assertions
        coVerify { fireBaseRepository.delete(id) }
    }

    @Test
    fun `entityExists() should call fireBaseRepository`() = runTest {
        // Call
        repository.entityExists(id)

        // Assertions
        coVerify { fireBaseRepository.entityExists(id) }
    }

    @Test
    fun `fetchById() should call fireBaseRepository`() = runTest {
        // Call
        repository.fetchById(id)

        // Assertions
        coVerify { fireBaseRepository.simpleDocumentFetch(id) }
    }

}