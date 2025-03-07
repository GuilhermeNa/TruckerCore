package com.example.truckercore.unit.model.modules.fleet.shared.module.licensing.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.model.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.CreateLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CreateLicensingUseCase
import com.example.truckercore.modules.user.entity.User
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

class CreateLicensingUseCaseImplTest : KoinTest {

    private val requirePermission: Permission = Permission.CREATE_LICENSING
    private val permissionService: PermissionService by inject()
    private val repository: com.example.truckercore.model.modules.fleet.shared.module.licensing.repository.LicensingRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: LicensingMapper by inject()
    private val useCase: CreateLicensingUseCase by inject()

    private val user = mockk<User>()
    private val licensing = mockk<Licensing>()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<com.example.truckercore.model.modules.fleet.shared.module.licensing.repository.LicensingRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<LicensingMapper> { mockk() }
                        single<CreateLicensingUseCase> {
                            CreateLicensingUseCaseImpl(
                                Permission.CREATE_LICENSING,
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
    fun `execute() should return success when licensing is correctly created`() = runTest {
        // Arrange
        val dto = mockk<LicensingDto>()
        val id = "newLicensingObjectId"
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            permissionService.canPerformAction(user, requirePermission)
            validatorService.validateForCreation(licensing)
            mapper.toDto(licensing)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, licensing).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requirePermission)
        }
    }

}
