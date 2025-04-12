package com.example.truckercore.unit.model.modules.person.employee.driver.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.model.modules.person.employee.driver.entity.Driver
import com.example.truckercore.model.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.person.employee.driver.repository.DriverRepository
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.GetDriverUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class GetDriverUseCaseImplTestTest : KoinTest {

    private val requiredPermission = Permission.VIEW_DRIVER
    private val permissionService: PermissionService by inject()
    private val repository: DriverRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: DriverMapper by inject()
    private val useCase: GetDriverUseCase by inject()

    private val user: User = mockk(relaxed = true)
    private val docParams: DocumentParameters = mockk(relaxed = true) {
        every { user } returns this@GetDriverUseCaseImplTestTest.user
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<DriverRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<DriverMapper> { mockk() }
                        single<GetDriverUseCase> {
                            GetDriverUseCaseImpl(
                                Permission.VIEW_DRIVER,
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
    fun `execute(DocumentParameters) should return Success when user has auth and data exists`() =
        runTest {
            // Arrange
            val dto: DriverDto = mockk(relaxed = true)
            val entity: Driver = mockk(relaxed = true)

            every { permissionService.canPerformAction(any(), any()) } returns true
            every { repository.fetchByDocument(any()) } returns flowOf(AppResponse.Success(dto))
            every { validatorService.validateDto(any()) } returns Unit
            every { mapper.toEntity(dto) } returns entity

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertEquals(entity, (result as AppResponse.Success).data)
            verifyOrder {
                permissionService.canPerformAction(user, requiredPermission)
                repository.fetchByDocument(docParams)
                validatorService.validateDto(dto)
                mapper.toEntity(dto)
            }
        }

    @Test
    fun `execute(DocumentParameters) should return Empty when the repository doesn't find data`() =
        runTest {
            // Arrange
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { repository.fetchByDocument(any()) } returns flowOf(AppResponse.Empty)

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is AppResponse.Empty)
            verifyOrder {
                permissionService.canPerformAction(user, requiredPermission)
                repository.fetchByDocument(docParams)
            }
        }

    @Test
    fun `execute(DocumentParameters) should throw UnauthorizedAccessException when the user has no auth`() =
        runTest {
            // Arrange
            every { permissionService.canPerformAction(any(), any()) } returns false

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(docParams).single()
            }

            // Assertions
            verify {
                permissionService.canPerformAction(user, requiredPermission)
            }
        }


}
