package com.example.truckercore.unit.model.modules.fleet.trailer.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.model.modules.fleet.trailer.use_cases.implementations.CreateTrailerUseCaseImpl
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.CreateTrailerUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
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

class CreateTrailerUseCaseImplTest : KoinTest {

    private val requirePermission: Permission = Permission.CREATE_TRAILER
    private val permissionService: PermissionService by inject()
    private val repository: TrailerRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: TrailerMapper by inject()
    private val useCase: CreateTrailerUseCase by inject()

    private val user = mockk<User>()
    private val trailer = mockk<Trailer>()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<TrailerRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<TrailerMapper> { mockk() }
                        single<CreateTrailerUseCase> {
                            CreateTrailerUseCaseImpl(
                                Permission.CREATE_TRAILER,
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
    fun `execute() should return success when trailer is correctly created`() = runTest {
        // Arrange
        val dto = mockk<TrailerDto>()
        val id = "newTrailerObjectId"
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            permissionService.canPerformAction(user, requirePermission)
            validatorService.validateForCreation(trailer)
            mapper.toDto(trailer)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, trailer).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requirePermission)
        }
    }

}
