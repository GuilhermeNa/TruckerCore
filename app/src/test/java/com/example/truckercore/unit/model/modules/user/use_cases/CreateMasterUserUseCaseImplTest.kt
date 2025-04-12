package com.example.truckercore.unit.model.modules.user.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.repository.UserRepository
import com.example.truckercore.model.modules.user.use_cases.implementations.CreateMasterUserUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.interfaces.CreateMasterUserUseCase
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
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
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateMasterUserUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: UserRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: UserMapper by inject()
    private val useCase: CreateMasterUserUseCase by inject()

    private val masterUser = mockk<User>()

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
                        single<CreateMasterUserUseCase> {
                            CreateMasterUserUseCaseImpl(
                                get(), get(), get()
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
    fun `execute() should return success when master user is correctly created`() = runTest {
        // Arrange
        val dto = mockk<UserDto>()
        val id = "newMasterUserObjectId"
        every { masterUser.level } returns Level.MASTER
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(masterUser).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            validatorService.validateForCreation(masterUser)
            mapper.toDto(masterUser)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw InvalidStateException when master user level is MASTER`() =
        runTest {
            // Arrange
            every { masterUser.level } returns Level.MODERATOR

            // Call
            assertThrows<InvalidStateException> {
                useCase.execute(masterUser).single()
            }

            // Assertions
            verify { masterUser.level }
        }


}
