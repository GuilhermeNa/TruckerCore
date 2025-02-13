package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.CreateBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateBusinessCentralUseCaseImplTest {

    companion object {

        private lateinit var requirePermission: Permission
        private lateinit var permissionService: PermissionService
        private lateinit var repository: BusinessCentralRepository
        private lateinit var validatorService: ValidatorService
        private lateinit var mapper: BusinessCentralMapper
        private lateinit var useCase: CreateBusinessCentralUseCase

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            requirePermission = Permission.CREATE_BUSINESS_CENTRAL
            permissionService = mockk(relaxed = true)
            repository = mockk(relaxed = true)
            validatorService = mockk(relaxed = true)
            mapper = mockk(relaxed = true)
            useCase = CreateBusinessCentralUseCaseImpl(
                requirePermission, permissionService, repository, validatorService, mapper
            )
        }
    }

    @Test
    fun `execute() should return success when data correctly created`() = runTest {
        // Arrange
        val newId = "id"
        val validUser = mockk<User> {
            every { level } returns Level.MASTER
            every { permissions } returns hashSetOf(Permission.CREATE_BUSINESS_CENTRAL)
        }
        val entity = mockk<BusinessCentral>(relaxed = true)
        val dto = mockk<BusinessCentralDto>(relaxed = true) {
            every { id } returns newId
        }

        every { validatorService.validateForCreation(entity) } returns Unit
        every { mapper.toDto(entity) } returns dto
        every { repository.create(dto) } returns flowOf(Response.Success(newId))

        // Call
        val result = useCase.execute(validUser, entity).single()

        // Behavior
        assertTrue(result is Response.Success)
        assertEquals(newId, (result as Response.Success).data)

        verifyOrder {
            validatorService.validateForCreation(entity)
            mapper.toDto(entity)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw InvalidStateException when user is wrong state`() = runTest {
        // Arrange
        val invalidUser = mockk<User> {
            every { level } returns Level.DRIVER
        }
        val entity = mockk<BusinessCentral>(relaxed = true)

        // Call
        assertThrows<InvalidStateException> {
            useCase.execute(invalidUser, entity).single()
        }
    }

}