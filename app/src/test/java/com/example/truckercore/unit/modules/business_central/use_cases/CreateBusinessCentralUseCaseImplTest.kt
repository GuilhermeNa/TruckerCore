package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.errors.BusinessCentralValidationException
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.CreateBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.abstractions.ValidationException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateBusinessCentralUseCaseImplTest {

    private lateinit var repository: BusinessCentralRepository
    private lateinit var validatorService: ValidatorService
    private lateinit var mapper: BusinessCentralMapper
    private lateinit var useCase: CreateBusinessCentralUseCase
    private lateinit var entity: BusinessCentral
    private lateinit var dto: BusinessCentralDto
    private lateinit var id: String

    @BeforeEach
    fun setup() {
        mockStaticLog()
        repository = mockk<BusinessCentralRepository>(relaxed = true)
        validatorService = mockk<ValidatorService>(relaxed = true)
        validatorService = mockk<ValidatorService>(relaxed = true)
        mapper = mockk<BusinessCentralMapper>(relaxed = true)
        useCase = CreateBusinessCentralUseCaseImpl(repository, validatorService, mapper)
        entity = TestBusinessCentralDataProvider.getBaseEntity()
        dto = TestBusinessCentralDataProvider.getBaseDto()
        id = "newId"
    }

    @Test
    fun `execute() should return response success data is valid`() = runTest {
        // Behavior
        every { validatorService.validateForCreation(entity) } returns Unit
        every { mapper.toDto(entity) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(entity).single()

        // Behavior
        assertTrue(result is Response.Success)
        assertEquals(id, (result as Response.Success).data)

        coVerifyOrder {
            validatorService.validateForCreation(entity)
            mapper.toDto(entity)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should return an error when any error occurs in the flow`() =
        runTest {
            // Object
            val exception = BusinessCentralValidationException()

            // Behavior
            every { validatorService.validateForCreation(entity) } throws exception

            // Call
            val result = useCase.execute(entity).single()

            // Behavior
            assertTrue(result is Response.Error)
            assertTrue((result as Response.Error).exception is BusinessCentralValidationException)

        }


}