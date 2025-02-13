/*
package com.example.truckercore.unit.modules.user.repository

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.repository.UserRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRepositoryImplTest {

    private lateinit var fireBaseRepository: FirebaseRepository<UserDto>
    private lateinit var repository: UserRepository
    private lateinit var dto: UserDto
    private lateinit var id: String

    @BeforeEach
    fun setup() {
        fireBaseRepository = mockk(relaxed = true)
        repository = UserRepositoryImpl(fireBaseRepository)
        dto = TestUserDataProvider.getBaseDto()
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
        coVerify { fireBaseRepository.documentFetch(id) }
    }

}*/
