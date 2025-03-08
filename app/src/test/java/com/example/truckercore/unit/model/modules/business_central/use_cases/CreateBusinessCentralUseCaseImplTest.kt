package com.example.truckercore.unit.model.modules.business_central.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.model.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.model.modules.business_central.use_cases.implementations.CreateBusinessCentralUseCaseImpl
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class CreateBusinessCentralUseCaseImplTest : KoinTest {

    private val repository: BusinessCentralRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: BusinessCentralMapper by inject()
    private val useCase: CreateBusinessCentralUseCase by inject()

    private val bCentral = mockk<BusinessCentral>()

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
                        single<ValidatorService> { mockk() }
                        single<BusinessCentralMapper> { mockk() }
                        single<CreateBusinessCentralUseCase> {
                            CreateBusinessCentralUseCaseImpl(
                                Permission.CREATE_BUSINESS_CENTRAL,
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
    fun `execute() should return success when data correctly created`() = runTest {
        // Arrange
        val dto = mockk<BusinessCentralDto>()
        val id = "newObjectId"
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(bCentral).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            validatorService.validateForCreation(bCentral)
            mapper.toDto(bCentral)
            repository.create(dto)
        }
    }

}
