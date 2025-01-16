package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
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
import com.example.truckercore.shared.sealeds.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteBusinessCentralUseCaseImplTest {

    private lateinit var repository: BusinessCentralRepository
    private lateinit var checkExistence: CheckBusinessCentralExistenceUseCase
    private lateinit var permissionService: PermissionService
    private lateinit var useCase: DeleteBusinessCentralUseCase
    private lateinit var userWithPermission: User
    private lateinit var userWithoutPermission: User
    private lateinit var id: String

    @BeforeEach
    fun setup() {
        mockStaticLog()
        repository = mockk<BusinessCentralRepository>(relaxed = true)
        checkExistence = mockk<CheckBusinessCentralExistenceUseCase>(relaxed = true)
        permissionService = PermissionServiceImpl()
        useCase = DeleteBusinessCentralUseCaseImpl(repository, checkExistence, permissionService)
        userWithPermission = TestUserDataProvider.getBaseEntity()
            .copy(permissions = setOf(Permission.DELETE_BUSINESS_CENTRAL))
        userWithoutPermission = TestUserDataProvider.getBaseEntity()
            .copy(permissions = setOf())
        id = "newId"
    }

    @Test
    fun `execute() should return a response success when user has permission and object is found`() =
        runTest {
            // Object
            val mockk = spyk(useCase, recordPrivateCalls = true)
            val deleteResponse = Response.Success(Unit)
            val existsResponse = Response.Success(true)

            // Behavior
            coEvery {
                checkExistence.execute(userWithPermission, id)
            } returns flowOf(existsResponse)
            coEvery {
                repository.delete(id)
            } returns flowOf(deleteResponse)

            // Call
            val result = mockk.execute(userWithPermission, id).single()

            // Assertions
            assertTrue(result is Response.Success)
            coVerify {
                mockk["userHasPermission"](userWithPermission)
                mockk["checkEntityExists"](userWithPermission, id)
                mockk["handleCheckExistenceSuccess"](id, existsResponse)
                mockk["handleExistentObject"](id)
            }
        }

    @Test
    fun `execute() should return a response error when user has no permission to delete`() =
        runTest {
            // Object
            val mockk = spyk(useCase, recordPrivateCalls = true)

            // Call
            val result = mockk.execute(userWithoutPermission, id).single()

            // Assertions
            assertTrue(result is Response.Error)
            assertTrue((result as Response.Error).exception is UnauthorizedAccessException)
            coVerify {
                mockk["userHasPermission"](userWithoutPermission)
                mockk["handleUnauthorizedPermission"](userWithoutPermission, id)
            }
        }

    @Test
    fun `execute() should return a response error when existence check returns an error`() =
        runTest {
            // Object
            val mockk = spyk(useCase, recordPrivateCalls = true)
            val existenceResponse = Response.Error(NullPointerException())

            // Behavior
            coEvery { checkExistence.execute(userWithPermission, id) } returns flowOf(existenceResponse)

            // Call
            val result = mockk.execute(userWithPermission, id).single()

            // Assertions
            assertTrue(result is Response.Error)
            assertTrue((result as Response.Error).exception is NullPointerException)
            coVerify {
                mockk["userHasPermission"](userWithPermission)
                mockk["checkEntityExists"](userWithPermission, id)
                mockk["handleCheckExistenceFailure"](existenceResponse)
            }
        }

    @Test
    fun `execute() should return a response error when existence check returns an empty`() =
        runTest {
            // Object
            val mockk = spyk(useCase, recordPrivateCalls = true)
            val existenceResponse = Response.Empty

            // Behavior
            coEvery { checkExistence.execute(userWithPermission, id) } returns flowOf(existenceResponse)

            // Call
            val result = mockk.execute(userWithPermission, id).single()

            // Assertions
            assertTrue(result is Response.Error)
            assertTrue((result as Response.Error).exception is ObjectNotFoundException)
            coVerify {
                mockk["userHasPermission"](userWithPermission)
                mockk["checkEntityExists"](userWithPermission, id)
                mockk["handleCheckExistenceFailure"](existenceResponse)
            }
        }

    @Test
    fun `execute() should return a response error when id is blank`() =
        runTest {
            // Object
            val mockk = spyk(useCase, recordPrivateCalls = true)

            // Call
            val result = mockk.execute(userWithPermission, "").single()

            // Assertions
            assertTrue(result is Response.Error)
            assertTrue((result as Response.Error).exception is IllegalArgumentException)
        }

}