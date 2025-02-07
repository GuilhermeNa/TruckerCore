package com.example.truckercore.unit.shared.modules.storage_file.repositories

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepositoryImpl
import com.example.truckercore.shared.utils.parameters.QuerySettings
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StorageFileFirebaseRepositoryImplTest {

    private val fireBaseRepository: NewFireBaseRepository = mockk(relaxed = true)
    private val collection = Collection.FILE
    private lateinit var repository: StorageFileRepository

    private val dto = TestStorageFileDataProvider.getBaseDto()
    private val id = "testId"

    @BeforeEach
    fun setup() {
        repository = StorageFileRepositoryImpl(fireBaseRepository, collection)
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

    @Test
    fun `fetchById() should call fireBaseRepository`() = runTest {
        // Call
        repository.fetchById(id)

        // Assertions
        coVerify { fireBaseRepository.documentFetch(collection, id, StorageFileDto::class.java) }
    }

    @Test
    fun `fetchByQuery() should call fireBaseRepository`() = runTest {
        // Arrange
        val settings = QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, "parentId")

        // Call
        repository.fetchByQuery(settings)

        // Assertions
        coVerify {
            fireBaseRepository.queryFetch(
                collection,
                settings,
                clazz = StorageFileDto::class.java
            )
        }
    }

}