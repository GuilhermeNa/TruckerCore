/*
package com.example.truckercore.unit.modules.fleet.shared.module.licensing.repository

import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LicensingRepositoryImplTest {

    private val fireBaseRepository: FirebaseRepository = mockk(relaxed = true)
    private val collection = Collection.LICENSING
    private lateinit var repository: LicensingRepository

    private lateinit var dto: LicensingDto
    private lateinit var id: String

    @BeforeEach
    fun setup() {
        repository = LicensingRepositoryImpl(fireBaseRepository, collection)
        dto = TestLicensingDataProvider.getBaseDto()
        id = "testId"
    }

    @Test
    fun `create() should call fireBaseRepository`() = runTest {
        // Call
        repository.create(dto)

        // Assertions
        coVerify { fireBaseRepository.create(collection, dto) }
    }

    @Test
    fun `update() should call fireBaseRepository`() = runTest {
        // Call
        repository.update(dto)

        // Assertions
        coVerify { fireBaseRepository.update(collection, dto) }
    }

    @Test
    fun `delete() should call fireBaseRepository`() = runTest {
        // Call
        repository.delete(id)

        // Assertions
        coVerify { fireBaseRepository.delete(collection, id) }
    }

    @Test
    fun `entityExists() should call fireBaseRepository`() = runTest {
        // Call
        repository.entityExists(id)

        // Assertions
        coVerify { fireBaseRepository.entityExists(collection, id) }
    }

  */
/*  @Test
    fun `fetchById() should call fireBaseRepository`() = runTest {
        // Call
        repository.fetchByDocument(id)

        // Assertions
        coVerify { fireBaseRepository.documentFetch(collection, id, LicensingDto::class.java) }
    }*//*


}*/
