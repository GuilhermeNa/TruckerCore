package com.example.truckercore.unit.modules.person.employee.admin.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.person.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.GetAdminUseCaseImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class GetAdminUseCaseImplTest : KoinTest {

    private val requiredPermission = Permission.VIEW_ADMIN
    private val permissionService: PermissionService by inject()
    private val repository: AdminRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: AdminMapper by inject()
    private val useCase: GetAdminUseCase by inject()

    private val user: User = mockk(relaxed = true)
    private val docParams: DocumentParameters = mockk(relaxed = true) {
        every { user } returns this@GetAdminUseCaseImplTest.user
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
                        single<AdminRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<AdminMapper> { mockk() }
                        single<GetAdminUseCase> {
                            GetAdminUseCaseImpl(
                                Permission.VIEW_ADMIN,
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
            val dto: AdminDto = mockk(relaxed = true)
            val entity: Admin = mockk(relaxed = true)

            every { permissionService.canPerformAction(any(), any()) } returns true
            every { repository.fetchByDocument(any()) } returns flowOf(Response.Success(dto))
            every { validatorService.validateDto(any()) } returns Unit
            every { mapper.toEntity(dto) } returns entity

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertEquals(entity, (result as Response.Success).data)
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
            every { repository.fetchByDocument(any()) } returns flowOf(Response.Empty)

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
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
