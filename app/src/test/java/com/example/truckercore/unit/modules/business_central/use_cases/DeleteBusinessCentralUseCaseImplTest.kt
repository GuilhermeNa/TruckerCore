package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.DeleteBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeleteBusinessCentralUseCaseImplTest {

    companion object {

        private lateinit var requiredPermission: Permission
        private lateinit var permissionService: PermissionService
        private lateinit var repository: BusinessCentralRepository
        private lateinit var checkExistence: CheckBusinessCentralExistenceUseCase
        private lateinit var useCase: DeleteBusinessCentralUseCase

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            requiredPermission = Permission.DELETE_BUSINESS_CENTRAL
            permissionService = PermissionServiceImpl()
            repository = mockk(relaxed = true)
            checkExistence = mockk(relaxed = true)
            useCase = DeleteBusinessCentralUseCaseImpl(
                requiredPermission = requiredPermission,
                permissionService = permissionService,
                repository = repository,
                checkExistence = checkExistence
            )
        }
    }

    @Test
    fun `execute() should return success when user has permission and object is found`() = runTest {
        // Arrange
        val id = "id"
        val userWithPermissions = mockk<User>(relaxed = true) {
            every { permissions } returns hashSetOf(
                Permission.VIEW_BUSINESS_CENTRAL,
                Permission.DELETE_BUSINESS_CENTRAL
            )
        }
        val deleteResponse = Response.Success(Unit)
        val existsResponse = Response.Success(Unit)

        every { checkExistence.execute(userWithPermissions, id) } returns flowOf(existsResponse)
        every { repository.delete(id) } returns flowOf(deleteResponse)

        // Call
        val result = useCase.execute(userWithPermissions, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerify {
            checkExistence.execute(userWithPermissions, id)
            repository.delete(id)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when the user has no auth for view`() =
        runTest {
            // Arrange
            val id = "id"
            val userWithoutReadPermission = mockk<User> {
                every { permissions } returns hashSetOf(Permission.DELETE_BUSINESS_CENTRAL)
            }

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(userWithoutReadPermission, id).single()
            }
        }

    @Test
    fun `execute() should throw UnauthorizedAccessException when the user has no auth for delete`() =
        runTest {
            // Arrange
            val id = "id"
            val userWithoutReadPermission = mockk<User> {
                every { permissions } returns hashSetOf(Permission.VIEW_BUSINESS_CENTRAL)
            }

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(userWithoutReadPermission, id).single()
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when entity does not exist`() = runTest {
        // Arrange
        val id = "id"
        val checkExistenceResponse = Response.Empty
        val userWithPermissions = mockk<User> {
            every { permissions } returns hashSetOf(
                Permission.VIEW_BUSINESS_CENTRAL,
                Permission.DELETE_BUSINESS_CENTRAL
            )
        }

        every {
            checkExistence.execute(userWithPermissions, id)
        } returns flowOf(checkExistenceResponse)

        // Call
        assertThrows<ObjectNotFoundException> {
            useCase.execute(userWithPermissions, id).single()
        }
    }

}