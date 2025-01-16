package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.UpdateBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateBusinessCentralUseCaseImplTest {

    private lateinit var useCase: UpdateBusinessCentralUseCase
    private lateinit var checkExistence: CheckBusinessCentralExistenceUseCase
    private lateinit var repository: BusinessCentralRepository
    private lateinit var permissionService: PermissionService
    private lateinit var validatorService: ValidatorService
    private lateinit var mapper: BusinessCentralMapper
    private lateinit var entity: BusinessCentral
    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        mockStaticLog()
        repository = mockk()
        permissionService = PermissionServiceImpl()
        validatorService = mockk()
        checkExistence = mockk()
        mapper = mockk()
        useCase = UpdateBusinessCentralUseCaseImpl(
            repository,
            checkExistence,
            permissionService,
            validatorService,
            mapper
        )
        user = TestUserDataProvider.getBaseEntity()
            .copy(permissions = setOf(Permission.UPDATE_BUSINESS_CENTRAL))
        entity = TestBusinessCentralDataProvider.getBaseEntity()
    }

    @Test
    fun `execute() should return success response when entity exists and is updated successfully`() =
        runTest {
            // Object
            val existentResponse = Response.Success(true)
            val dto = TestBusinessCentralDataProvider.getBaseDto()
            val mockk = spyk(useCase, recordPrivateCalls = true)

            // Behavior
            coEvery { checkExistence.execute(user, entity.id!!) } returns flowOf(
                existentResponse
            )
            every { validatorService.validateEntity(entity) } returns Unit
            every { mapper.toDto(entity) } returns dto
            coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

            // Call
            val result = mockk.execute(user, entity).single()

            // Assertions
            assertTrue(result is Response.Success)
            coVerifyOrder {
                mockk["userHasPermission"](user)
                mockk["checkEntityExists"](user, entity)
                mockk["handleCheckExistenceSuccess"](entity, existentResponse)
                mockk["handleExistentObject"](entity)
                validatorService.validateEntity(entity)
                mapper.toDto(entity)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should return error when entity does not exist`() = runTest {
        // Object
        val response = Response.Success(false)

        val mockk = spyk(useCase, recordPrivateCalls = true)

        // Behavior
        coEvery { checkExistence.execute(user, entity.id!!) } returns flowOf(response)

        // Call
        val result = mockk.execute(user, entity).single()

        // Assertions
        assertTrue(result is Response.Error)
        assertTrue((result as Response.Error).exception is ObjectNotFoundException)
        coVerifyOrder {
            mockk["userHasPermission"](user)
            checkExistence.execute(user, entity.id!!)
            mockk["handleCheckExistenceSuccess"](entity, response)
            mockk["handleNonExistentObject"](entity)
        }
    }

    @Test
    fun `execute() should return error when check existence fails`() = runTest {
        // Object
        val response = Response.Error(NullPointerException("CheckExistence failed"))

        val mockk = spyk(useCase, recordPrivateCalls = true)

        // Behavior
        coEvery { checkExistence.execute(user, entity.id!!) } returns flowOf(response)

        // Call
        val result = mockk.execute(user, entity).single()

        // Assertions
        assertTrue(result is Response.Error)
        assertTrue((result as Response.Error).exception is NullPointerException)
        coVerifyOrder {
            mockk["userHasPermission"](user)
            checkExistence.execute(user, entity.id!!)
            mockk["handleCheckExistenceFailure"](response)
        }
    }

    @Test
    fun `execute() should return unauthorized error when user does not have permission`() =
        runTest {
            // Object
            val userWithoutPermission = TestUserDataProvider.getBaseEntity()
                .copy(permissions = setOf())

            // Call
            val result = useCase.execute(userWithoutPermission, entity).single()

            // Assertions
            assertTrue(result is Response.Error)
            assertTrue((result as Response.Error).exception is UnauthorizedAccessException)
        }

    @Test
    fun `execute() should handle unexpected errors`() = runTest {
        // Simulate an unexpected error
        val exception = RuntimeException("Unexpected exception")
        coEvery { checkExistence.execute(user, entity.id!!) } throws exception

        // Call the use case and assert the error response
        val result = useCase.execute(user, entity).single()

        // Assertions
        assertTrue(result is Response.Error)
        assertTrue((result as Response.Error).exception is RuntimeException)
    }

}