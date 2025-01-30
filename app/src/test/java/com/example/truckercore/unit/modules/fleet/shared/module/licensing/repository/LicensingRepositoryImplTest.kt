package com.example.truckercore.unit.modules.fleet.shared.module.licensing.repository

import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LicensingRepositoryImplTest {

    private lateinit var fireBaseRepository: FirebaseRepository<LicensingDto>
    private lateinit var repository: LicensingRepository
    private lateinit var dto: LicensingDto
    private lateinit var id: String

    @BeforeEach
    fun setup() {
        fireBaseRepository = mockk(relaxed = true)
        repository = LicensingRepositoryImpl(fireBaseRepository)
        dto = TestLicensingDataProvider.getBaseDto()
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