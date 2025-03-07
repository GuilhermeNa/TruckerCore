package com.example.truckercore.unit.model.modules.person.employee.admin.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.person.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.UpdateAdminUseCaseImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.UpdateAdminUseCase
import com.example.truckercore.modules.user.entity.User
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

class UpdateAdminUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: AdminRepository by inject()
    private val checkExistence: CheckAdminExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: AdminMapper by inject()
    private val useCase: UpdateAdminUseCase by inject()

    private val id = "adminId"
    private val admin: Admin = mockk()
    private val dto: AdminDto = mockk()
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
                        single<AdminRepository> { mockk() }
                        single<CheckAdminExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<AdminMapper> { mockk() }
                        single<UpdateAdminUseCase> {
                            UpdateAdminUseCaseImpl(
                                Permission.UPDATE_ADMIN,
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
    fun `execute() should return success when admin exists and is updated successfully`() =
        runTest {
            // Arrange
            every { admin.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, admin).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                admin.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
                validatorService.validateEntity(admin)
                mapper.toDto(admin)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the Admin id is null`() =
        runTest {
            // Arrange
            every { admin.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, admin).single()
            }

            // Assertions
            verify {
                admin.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { admin.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, admin).single()
            }

            // Assertions
            verifyOrder {
                admin.id
                checkExistence.execute(user, id)
            }
        }

}
