package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.UpdateUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.UpdateUserUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class UpdateUserUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: UserRepository by inject()
    private val checkExistence: CheckUserExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: UserMapper by inject()
    private val useCase: UpdateUserUseCase by inject()

    private val id = "userId"
    private val userToUpdate: User = mockk()
    private val dto: UserDto = mockk()
    private val user: User = mockk()

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
                        single<CheckUserExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<UserMapper> { mockk() }
                        single<UpdateUserUseCase> {
                            UpdateUserUseCaseImpl(
                                Permission.UPDATE_USER,
                                get(), get(), get(), get(), get()
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
    fun `execute() should return success when user exists and is updated successfully`() =
        runTest {
            // Arrange
            every { userToUpdate.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, userToUpdate).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                userToUpdate.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_USER)
                validatorService.validateEntity(userToUpdate)
                mapper.toDto(userToUpdate)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the User id is null`() =
        runTest {
            // Arrange
            every { userToUpdate.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, userToUpdate).single()
            }

            // Assertions
            verify {
                userToUpdate.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { userToUpdate.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, userToUpdate).single()
            }

            // Assertions
            verifyOrder {
                userToUpdate.id
                checkExistence.execute(user, id)
            }
        }

}
