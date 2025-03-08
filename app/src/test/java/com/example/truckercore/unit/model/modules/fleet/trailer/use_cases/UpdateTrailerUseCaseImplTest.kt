package com.example.truckercore.unit.model.modules.fleet.trailer.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.model.modules.fleet.trailer.use_cases.implementations.UpdateTrailerUseCaseImpl
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.CheckTrailerExistenceUseCase
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.UpdateTrailerUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class UpdateTrailerUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: TrailerRepository by inject()
    private val checkExistence: CheckTrailerExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: TrailerMapper by inject()
    private val useCase: UpdateTrailerUseCase by inject()

    private val id = "trailerId"
    private val trailer: Trailer = mockk()
    private val dto: TrailerDto = mockk()
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
                        single<TrailerRepository> { mockk() }
                        single<CheckTrailerExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<TrailerMapper> { mockk() }
                        single<UpdateTrailerUseCase> {
                            UpdateTrailerUseCaseImpl(
                                Permission.UPDATE_TRAILER,
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
    fun `execute() should return success when trailer exists and is updated successfully`() =
        runTest {
            // Arrange
            every { trailer.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, trailer).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                trailer.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
                validatorService.validateEntity(trailer)
                mapper.toDto(trailer)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the Trailer id is null`() =
        runTest {
            // Arrange
            every { trailer.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, trailer).single()
            }

            // Assertions
            verify {
                trailer.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { trailer.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, trailer).single()
            }

            // Assertions
            verifyOrder {
                trailer.id
                checkExistence.execute(user, id)
            }
        }

}
