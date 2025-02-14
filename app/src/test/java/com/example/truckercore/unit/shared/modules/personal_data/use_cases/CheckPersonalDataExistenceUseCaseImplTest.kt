/*
package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.CheckPersonalDataExistenceUseCaseImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CheckPersonalDataExistenceUseCaseImplTest {

    private val repository: PersonalDataRepository = mockk()
    private val permissionService: PermissionService = mockk()
    private lateinit var useCase: CheckPersonalDataExistenceUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CheckPersonalDataExistenceUseCaseImpl(
            repository,
            permissionService,
            Permission.VIEW_PERSONAL_DATA
        )
    }

    @Test
    fun `should return success when user has permission and object exists`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
    }

    @Test
    fun `should return empty when object does not exist`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
    }

    @Test
    fun `should return error when user does not have permission`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
    }

    @Test
    fun `should return error when id is blank`() = runTest {
        // Call
        val result = useCase.execute(user, "").single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is IllegalArgumentException)
    }

}*/
