package com.example.truckercore.unit.modules.fleet.trailer.repository

import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TrailerRepositoryImplTest {

    private lateinit var repository: TrailerRepository
    private val fireBaseRepository: FirebaseRepository<TrailerDto> = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        repository = TrailerRepositoryImpl(fireBaseRepository)
    }

    @Test
    fun `should call fireBase repository create`() = runTest {
        // Arrange
        val dto = TestTrailerDataProvider.getBaseDto()

        // Call
        repository.create(dto)

        // Assertion
        coVerify { fireBaseRepository.create(dto) }
    }

    @Test
    fun `should call fireBase repository update`() = runTest {
        // Arrange
        val dto = TestTrailerDataProvider.getBaseDto()

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

}