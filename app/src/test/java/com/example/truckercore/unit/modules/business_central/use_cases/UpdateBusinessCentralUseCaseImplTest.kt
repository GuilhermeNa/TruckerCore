package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
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
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UpdateBusinessCentralUseCaseImplTest {

    companion object {

        private lateinit var requiredPermission: Permission
        private lateinit var useCase: UpdateBusinessCentralUseCase
        private lateinit var checkExistence: CheckBusinessCentralExistenceUseCase
        private lateinit var repository: BusinessCentralRepository
        private lateinit var permissionService: PermissionService
        private lateinit var validatorService: ValidatorService
        private lateinit var mapper: BusinessCentralMapper

        @JvmStatic
        @BeforeAll
        fun setUp() {
            mockStaticLog()
            requiredPermission = Permission.UPDATE_BUSINESS_CENTRAL
            permissionService = PermissionServiceImpl()
            checkExistence = mockk(relaxed = true)
            validatorService = mockk(relaxed = true)
            mapper = mockk(relaxed = true)
            repository = mockk(relaxed = true)
            useCase = UpdateBusinessCentralUseCaseImpl(
                requiredPermission = Permission.UPDATE_BUSINESS_CENTRAL,
                permissionService = permissionService,
                repository = repository,
                checkExistence = checkExistence,
                validatorService = validatorService,
                mapper = mapper
            )
        }

    }

    @Test
    fun `execute() should return success when entity exists and is updated successfully`() =
        runTest {
            // Arrange
            val userId = "id"
            val validUser = mockk<User>(relaxed = true) {
                every { id } returns userId
                every { permissions } returns hashSetOf(Permission.UPDATE_BUSINESS_CENTRAL)
            }
            val bCentral = mockk<BusinessCentral>(relaxed = true)
            val bCentralDto = mockk<BusinessCentralDto>(relaxed = true)
            val existenceResponse = Response.Success(Unit)
            val updateResponse = Response.Success(Unit)

            every { checkExistence.execute(validUser, userId) } returns flowOf(existenceResponse)
            every { validatorService.validateEntity(bCentral) } returns Unit
            every { mapper.toDto(bCentral) } returns bCentralDto
            every { repository.update(bCentralDto) } returns flowOf(updateResponse)

            // Call
            val result = useCase.execute(validUser, bCentral).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                checkExistence.execute(validUser, userId)
                validatorService.validateEntity(bCentral)
                mapper.toDto(bCentral)
                repository.update(bCentralDto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when id is null`() = runTest {
        // Arrange
        val validUser = mockk<User>(relaxed = true)
        val bCentral = mockk<BusinessCentral>(relaxed = true) {
            every { id } returns null
        }

        // Call
        assertThrows<NullPointerException> {
            useCase.execute(validUser, bCentral)
        }

    }

    @Test
    fun `execute() should throw ObjectNotFoundException when object does not exists`() = runTest {
        // Arrange
        val validUser = mockk<User>(relaxed = true) {
            every { permissions } returns hashSetOf()
        }
        val bCentralId = "id"
        val bCentral = mockk<BusinessCentral>(relaxed = true) {
            every { id } returns bCentralId
        }
        val existenceResponse = Response.Empty

        every { checkExistence.execute(validUser, bCentralId) } returns flowOf(existenceResponse)

        // Call
        assertThrows<ObjectNotFoundException> {
            useCase.execute(validUser, bCentral)
        }
    }

}