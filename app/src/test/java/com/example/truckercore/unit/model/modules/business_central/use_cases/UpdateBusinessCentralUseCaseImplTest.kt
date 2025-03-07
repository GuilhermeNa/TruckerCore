package com.example.truckercore.unit.model.modules.business_central.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.UpdateBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class UpdateBusinessCentralUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: BusinessCentralRepository by inject()
    private val checkExistence: CheckBusinessCentralExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: BusinessCentralMapper by inject()
    private val useCase: UpdateBusinessCentralUseCase by inject()

    private val id = "objId"
    private val bCentral: BusinessCentral = mockk()
    private val dto: BusinessCentralDto = mockk()
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
                        single<BusinessCentralRepository> { mockk() }
                        single<CheckBusinessCentralExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<BusinessCentralMapper> { mockk() }
                        single<UpdateBusinessCentralUseCase> {
                            UpdateBusinessCentralUseCaseImpl(
                                Permission.UPDATE_BUSINESS_CENTRAL,
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
    fun `execute() should return success when entity exists and is updated successfully`() =
        runTest {
            // Arrange
            every { bCentral.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, bCentral).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                bCentral.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_BUSINESS_CENTRAL)
                validatorService.validateEntity(bCentral)
                mapper.toDto(bCentral)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the BusinessCentral id is null`() =
        runTest {
            // Arrange
            every { bCentral.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, bCentral).single()
            }

            // Assertions
            verify {
                bCentral.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { bCentral.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, bCentral).single()
            }

            // Assertions
            verifyOrder {
                bCentral.id
                checkExistence.execute(user, id)
            }
        }

}
