package com.example.truckercore.unit.shared.modules.personal_data.repositories

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PersonalDataFirebaseRepositoryImplTestImpl {

    private val fireBaseRepository: FirebaseRepository<PersonalDataDto> = mockk(relaxed = true)
    private lateinit var repository: PersonalDataRepository
    private val dto = TestPersonalDataDataProvider.getBaseDto()
    private val id = "testId"
    private val parentId = "testParentId"

    @BeforeEach
    fun setup() {
        repository = PersonalDataRepositoryImpl(fireBaseRepository)
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