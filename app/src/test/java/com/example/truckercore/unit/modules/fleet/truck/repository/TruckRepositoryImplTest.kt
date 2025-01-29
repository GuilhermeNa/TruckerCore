package com.example.truckercore.unit.modules.fleet.truck.repository

import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.repository.TruckRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TruckRepositoryImplTest {

    private lateinit var repository: TruckRepository
    private lateinit var fireBaseRepository: FirebaseRepository<TruckDto>

    @BeforeEach
    fun setup() {
        fireBaseRepository = mockk(relaxed = true)
        repository = TruckRepositoryImpl(fireBaseRepository)
    }

    @Test
    fun `should call fireBase repository create`() = runTest {
        // Arrange
        val dto = TestTruckDataProvider.getBaseDto()

        // Call
        repository.create(dto)

        // Assertion
        coVerify { fireBaseRepository.create(dto) }
    }

    @Test
    fun `should call fireBase repository update`() = runTest {
        // Arrange
        val dto = TestTruckDataProvider.getBaseDto()

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
        coVerify { fireBaseRepository.simpleDocumentFetch(id) }
    }

}