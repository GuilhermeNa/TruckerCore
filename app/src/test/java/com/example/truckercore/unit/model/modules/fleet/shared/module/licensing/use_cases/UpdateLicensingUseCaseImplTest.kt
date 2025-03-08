package com.example.truckercore.unit.model.modules.fleet.shared.module.licensing.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.model.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.implementations.UpdateLicensingUseCaseImpl
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces.UpdateLicensingUseCase
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

class UpdateLicensingUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: LicensingRepository by inject()
    private val checkExistence: CheckLicensingExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: LicensingMapper by inject()
    private val useCase: UpdateLicensingUseCase by inject()

    private val id = "licensingId"
    private val licensing: Licensing = mockk()
    private val dto: LicensingDto = mockk()
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
                        single<LicensingRepository> { mockk() }
                        single<CheckLicensingExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<LicensingMapper> { mockk() }
                        single<UpdateLicensingUseCase> {
                            UpdateLicensingUseCaseImpl(
                                Permission.UPDATE_LICENSING,
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
    fun `execute() should return success when licensing exists and is updated successfully`() =
        runTest {
            // Arrange
            every { licensing.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, licensing).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                licensing.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
                validatorService.validateEntity(licensing)
                mapper.toDto(licensing)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the Licensing id is null`() =
        runTest {
            // Arrange
            every { licensing.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, licensing).single()
            }

            // Assertions
            verify {
                licensing.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { licensing.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, licensing).single()
            }

            // Assertions
            verifyOrder {
                licensing.id
                checkExistence.execute(user, id)
            }
        }

}
