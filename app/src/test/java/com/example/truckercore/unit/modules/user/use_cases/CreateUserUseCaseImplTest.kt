package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.CreateUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CreateUserUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateUserUseCaseImplTest : KoinTest {

    private val requirePermission: Permission = Permission.CREATE_USER
    private val permissionService: PermissionService by inject()
    private val repository: UserRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: UserMapper by inject()
    private val useCase: CreateUserUseCase by inject()

    private val user = mockk<User>()
    private val newUser = mockk<User>()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<UserRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<UserMapper> { mockk() }
                        single<CreateUserUseCase> {
                            CreateUserUseCaseImpl(
                                Permission.CREATE_USER,
                                get(), get(), get(), get()
                            )
                        }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()
    }

    @Test
    fun `execute() should return success when user is correctly created`() = runTest {
        // Arrange
        val dto = mockk<UserDto>()
        val id = "newUserObjectId"
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, newUser).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            permissionService.canPerformAction(user, requirePermission)
            validatorService.validateForCreation(newUser)
            mapper.toDto(newUser)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, newUser).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requirePermission)
        }
    }

}
