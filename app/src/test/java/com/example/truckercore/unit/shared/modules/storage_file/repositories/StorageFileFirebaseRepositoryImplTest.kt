package com.example.truckercore.unit.shared.modules.storage_file.repositories

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StorageFileFirebaseRepositoryImplTest {

    private val fireBaseRepository: FirebaseRepository<StorageFileDto> = mockk(relaxed = true)
    private lateinit var repository: StorageFileRepository
    private val dto = TestStorageFileDataProvider.getBaseDto()
    private val id = "testId"
    private val parentId = "testParentId"

    @BeforeEach
    fun setup() {
        repository = StorageFileRepositoryImpl(fireBaseRepository)
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
        coVerify { fireBaseRepository.documentFetch(id) }
    }

    @Test
    fun `fetchByParentId() should call fireBaseRepository`() = runTest {
        // Call
        repository.fetchByParentId(parentId)

        // Assertions
        coVerify { fireBaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId) }
    }

}