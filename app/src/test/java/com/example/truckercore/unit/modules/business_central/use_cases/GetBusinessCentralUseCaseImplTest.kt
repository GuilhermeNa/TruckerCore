package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.GetBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetBusinessCentralUseCaseImplTest {

    companion object {

        private lateinit var requirePermission: Permission
        private lateinit var permissionService: PermissionService
        private lateinit var repository: BusinessCentralRepository
        private lateinit var validatorService: ValidatorService
        private lateinit var mapper: BusinessCentralMapper
        private lateinit var useCase: GetBusinessCentralUseCase

        @JvmStatic
        @BeforeAll
        fun setUp() {
            mockStaticLog()
            requirePermission = Permission.VIEW_BUSINESS_CENTRAL
            permissionService = PermissionServiceImpl()
            repository = mockk(relaxed = true)
            validatorService = mockk(relaxed = true)
            mapper = mockk(relaxed = true)
            useCase = GetBusinessCentralUseCaseImpl(
                requiredPermission = requirePermission,
                permissionService = permissionService,
                repository = repository,
                validatorService = validatorService,
                mapper = mapper
            )
        }
    }

    @Test
    fun `execute(DocumentParameters) should return Success when user has auth and data exists`() =
        runTest {
            // Arrange
            val user = mockk<User> {
                every { permissions } returns hashSetOf(Permission.VIEW_BUSINESS_CENTRAL)
            }
            val dto = mockk<BusinessCentralDto>()
            val entity = mockk<BusinessCentral>()
            val repositoryResponse = Response.Success(dto)
            val params = DocumentParameters.create(user)
                .setStream(false).setId("id").build()
            val expectedResult = Response.Success(entity)

            every { repository.fetchByDocument(params) } returns flowOf(repositoryResponse)
            every { validatorService.validateDto(dto) } returns Unit
            every { mapper.toEntity(dto) } returns entity

            // Call
            val result = useCase.execute(params).single()

            // Assertions
            assertEquals(expectedResult, result)
            verifyOrder {
                repository.fetchByDocument(params)
                validatorService.validateDto(repositoryResponse.data)
                mapper.toEntity(repositoryResponse.data)
            }
        }

    @Test
    fun `execute(DocumentParameters) should throw UnauthorizedAccessException when the user has no auth`() =
        runTest {
            // Arrange
            val userWithoutPermission = mockk<User> {
                every { permissions } returns hashSetOf()
            }
            val params = mockk<DocumentParameters>(relaxed = true) {
                every { user } returns userWithoutPermission

            }

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(params).single()
            }

        }

    @Test
    fun `execute(QueryParameters) should return Success when user has auth and data exists`() =
        runTest {
            // Arrange
            val validUser = mockk<User>(relaxed = true) {
                every { permissions } returns hashSetOf(Permission.VIEW_BUSINESS_CENTRAL)
            }
            val entity = mockk<BusinessCentral>(relaxed = true)
            val dto = mockk<BusinessCentralDto>(relaxed = true)

            val dtoList = listOf(dto)
            val entityList = listOf(entity)

            val params = mockk<QueryParameters>(relaxed = true) {
                every { user } returns validUser
            }

            val repositoryResponse = Response.Success(dtoList)
            val expectedResult = Response.Success(entityList)

            every { repository.fetchByQuery(params) } returns flowOf(repositoryResponse)
            every { validatorService.validateDto(any()) } returns Unit
            every { mapper.toEntity(dto) } returns entity

            // Call
            val result = useCase.execute(params).single()

            // Assertions
            assertEquals(expectedResult, result)
            verifyOrder {
                repository.fetchByQuery(params)
                validatorService.validateDto(dto)
                mapper.toEntity(dto)
            }
        }

    @Test
    fun `execute(QueryParameters) should throw UnauthorizedAccessException when the user has no auth`() =
        runTest {
            // Arrange
            val userWithoutPermission = mockk<User> {
                every { permissions } returns hashSetOf()
            }
            val params = mockk<QueryParameters>(relaxed = true) {
                every { user } returns userWithoutPermission
            }

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(params).single()
            }

        }

}