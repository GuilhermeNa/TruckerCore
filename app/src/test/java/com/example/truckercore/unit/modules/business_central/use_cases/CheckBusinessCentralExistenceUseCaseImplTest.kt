package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.CheckBusinessCentralExistenceUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CheckBusinessCentralExistenceUseCaseImplTest {

    private lateinit var permissionService: PermissionServiceImpl
    private lateinit var repository: BusinessCentralRepository
    private lateinit var useCase: CheckBusinessCentralExistenceUseCase
    private lateinit var userWithPermission: User
    private lateinit var userWithoutPermission: User
    private val id = "testId"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        permissionService = PermissionServiceImpl()
        repository = mockk<BusinessCentralRepository>()
        useCase = CheckBusinessCentralExistenceUseCaseImpl(repository, permissionService)
        userWithPermission =
            TestUserDataProvider.getBaseEntity()
                .copy(permissions = setOf(Permission.VIEW_BUSINESS_CENTRAL))
        userWithoutPermission =
            TestUserDataProvider.getBaseEntity()
                .copy(permissions = setOf())
    }

    @Test
    fun `execute() should return a response success with true when object is found`() = runTest {
        // Behavior
        coEvery { repository.entityExists(id) } returns flowOf(Response.Success(true))

        // Call
        val response = useCase.execute(userWithPermission, id).single()

        // Assertions
        assertTrue(response is Response.Success)
        assertEquals(true, (response as Response.Success).data)

    }

    @Test
    fun `execute() should return a response error when user has no authorization`() = runTest {
        // Call
        val response = useCase.execute(userWithoutPermission, id).single()

        // Assertions
        assertTrue(response is Response.Error)
        assertTrue((response as Response.Error).exception is UnauthorizedAccessException)
    }

    @Test
    fun `execute() should return a response error when received id is empty`() = runTest {
        // Call
        val response = useCase.execute(userWithoutPermission, "").single()

        // Assertions
        assertTrue(response is Response.Error)
        assertTrue((response as Response.Error).exception is IllegalArgumentException)
    }

    @Test
    fun `execute() should return a response error with ObjectNotFoundException when entity exists returns empty`() =
        runTest {
            //Behavior
            coEvery { repository.entityExists(id) } returns flowOf(Response.Empty)

            // Call
            val response = useCase.execute(userWithPermission, id).single()

            // Assertions
            assertTrue(response is Response.Error)
            assertTrue((response as Response.Error).exception is ObjectNotFoundException)
        }

    @Test
    fun `execute() should return a response error when the repository encounters an error`() =
        runTest {
            //Behavior
            coEvery {
                repository.entityExists(id)
            } returns flowOf(Response.Error(NullPointerException("SimulatedException")))

            // Call
            val response = useCase.execute(userWithPermission, id).single()

            // Assertions
            assertTrue(response is Response.Error)
            assertTrue((response as Response.Error).exception is NullPointerException)
        }

}