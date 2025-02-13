package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.CheckBusinessCentralExistenceUseCaseImpl
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class CheckBusinessCentralExistenceUseCaseImplTest {

    private val requirePermission = Permission.VIEW_BUSINESS_CENTRAL
    private val permissionService = PermissionServiceImpl()
    private val repository: BusinessCentralRepository = mockk(relaxed = true)

    private val useCase = CheckBusinessCentralExistenceUseCaseImpl(
        requirePermission, permissionService, repository
    )

    private val userWithPermission =
        TestUserDataProvider.getBaseEntity()
            .copy(permissions = hashSetOf(Permission.VIEW_BUSINESS_CENTRAL))

    private val userWithoutPermission =
        TestUserDataProvider.getBaseEntity().copy(permissions = hashSetOf())

    private val id = "testId"

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun `execute() should return a response success with true when object is found`() = runTest {
        // Behavior
        every { repository.entityExists(id) } returns flowOf(Response.Success(Unit))

        // Call
        val response = useCase.execute(userWithPermission, id).single()

        // Assertions
        assertTrue(response is Response.Success)

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
    fun `execute() should return empty when the object was not found`() =
        runTest {
            //Behavior
            every { repository.entityExists(id) } returns flowOf(Response.Empty)

            // Call
            val response = useCase.execute(userWithPermission, id).single()

            // Assertions
            assertTrue(response is Response.Empty)
        }

}