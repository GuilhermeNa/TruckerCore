package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.GetBusinessCentralByIdUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetBusinessCentralByIdUseCaseImplTest {

    private lateinit var repository: BusinessCentralRepository
    private lateinit var permissionService: PermissionService
    private lateinit var validatorService: ValidatorService
    private lateinit var mapper: BusinessCentralMapper
    private lateinit var useCase: GetBusinessCentralByIdUseCase

    private lateinit var user: User
    private val id = "id"

    @BeforeEach
    fun setUp() {
        mockStaticLog()
        repository = mockk()
        permissionService = PermissionServiceImpl()
        validatorService = mockk(relaxed = true)
        mapper = mockk(relaxed = true)
        useCase = GetBusinessCentralByIdUseCaseImpl(repository, permissionService, validatorService, mapper)
        user = TestUserDataProvider.getBaseEntity()
            .copy(permissions = setOf(Permission.VIEW_BUSINESS_CENTRAL))
    }

    @Test
    fun `execute() should return a response with BusinessCentral when repository fetches data successfully`()
    = runTest {
        // Object
        val entity = TestBusinessCentralDataProvider.getBaseEntity()
        val dto = TestBusinessCentralDataProvider.getBaseDto()
        val response = Response.Success(dto)
        val mockk = spyk(useCase, recordPrivateCalls = true)

        // Behavior
        every { validatorService.validateDto(dto) } returns Unit
        coEvery { repository.fetchById(id) } returns flowOf(response)
        every { mapper.toEntity(dto) } returns entity

        // Call
        val result = mockk.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        assertEquals(entity, (result as Response.Success).data)
        coVerifyOrder {
            mockk["userHasPermission"](user)
            repository.fetchById(id)
            mockk["processResponse"](response)
            mockk["handleSuccessResponse"](response)
            validatorService.validateDto(response.data)
            mapper.toEntity(response.data)
        }

    }

    @Test
    fun `execute() should return a response error when repository fetches data with error`()
    = runTest {
        // Object
        val response = Response.Error(Exception("Database error"))
        val mockk = spyk(useCase, recordPrivateCalls = true)

        // Behavior
        coEvery { repository.fetchById(id) } returns flowOf(response)

        // Call
        val result = mockk.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error)
        coVerifyOrder {
            mockk["userHasPermission"](user)
            repository.fetchById(id)
            mockk["processResponse"](response)
            mockk["handleFailureResponse"](response)
        }
    }

    @Test
    fun `execute() should return an empty response when repository returns an empty response`()
    = runTest {
        // Object
        val response = Response.Empty
        val mockk = spyk(useCase, recordPrivateCalls = true)

        // Behavior
        coEvery { repository.fetchById(id) } returns flowOf(response)

        // Call
        val result = mockk.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            mockk["userHasPermission"](user)
            repository.fetchById(id)
            mockk["processResponse"](response)
            mockk["handleEmptyResponse"](response)
        }
    }

    @Test
    fun `execute() should return unauthorized error when user does not have permission`()
    = runTest {
        // Object
        val response = Response.Error(UnauthorizedAccessException())
        val userWithoutPermission = TestUserDataProvider.getBaseEntity()
            .copy(permissions = setOf())

        // Call
        val result = useCase.execute(userWithoutPermission, id).single()

        // Assertions
        assertTrue(result is Response.Error)
        assertTrue((result as Response.Error).exception is UnauthorizedAccessException)
        }

    @Test
    fun `execute() should handle unexpected errors`() = runTest {
        // Simulate an unexpected error
        val exception = RuntimeException("Unexpected exception")
        coEvery { repository.fetchById(id) } throws exception

        // Call the use case and assert the error response
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error)
        assertTrue((result as Response.Error).exception is RuntimeException)
    }

}