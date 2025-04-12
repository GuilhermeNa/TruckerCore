package com.example.truckercore.unit.model.modules.user.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.repository.UserRepository
import com.example.truckercore.model.modules.user.use_cases.implementations.GetUserUseCaseImpl
import com.example.truckercore.model.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
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

class GetUserUseCaseImplTest : KoinTest {

    private val requiredPermission = Permission.VIEW_USER
    private val permissionService: PermissionService by inject()
    private val repository: UserRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: UserMapper by inject()
    private val useCase: GetUserUseCase by inject()

    private val user: User = mockk(relaxed = true)
    private val docParams: DocumentParameters = mockk(relaxed = true) {
        every { user } returns this@GetUserUseCaseImplTest.user
    }
    private val queryParams: QueryParameters = mockk(relaxed = true) {
        every { user } returns this@GetUserUseCaseImplTest.user
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
                        single<UserRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<UserMapper> { mockk() }
                        single<GetUserUseCase> {
                            GetUserUseCaseImpl(
                                Permission.VIEW_USER,
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
    fun `execute(userId, shouldStream) should return Success when user has auth and data exists`() =
        runTest {
            // Arrange
            val dto: UserDto = mockk(relaxed = true)
            val entity: User = mockk(relaxed = true)

            every { repository.fetchLoggedUser(any(), any()) } returns flowOf(AppResponse.Success(dto))
            every { validatorService.validateDto(any()) } returns Unit
            every { mapper.toEntity(dto) } returns entity

            // Call
            val result = useCase.execute("userId", true).single()

            // Assertions
            assertEquals(entity, (result as AppResponse.Success).data)
            verifyOrder {
                repository.fetchLoggedUser("userId", true)
                validatorService.validateDto(dto)
                mapper.toEntity(dto)
            }
        }

    @Test
    fun `execute(userId, shouldStream) should return Empty when the repository doesn't find data`() =
        runTest {
            // Arrange
            every { repository.fetchLoggedUser(any(), any()) } returns flowOf(AppResponse.Empty)

            // Call
            val result = useCase.execute("userId", true).single()

            // Assertions
            assertTrue(result is AppResponse.Empty)
            verify {
                repository.fetchLoggedUser("userId", true)
            }
        }

    @Test
    fun `execute(DocumentParameters) should return Success when user has auth and data exists`() =
        runTest {
            // Arrange
            val dto: UserDto = mockk(relaxed = true)
            val entity: User = mockk(relaxed = true)

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

    @Test
    fun `execute(QueryParameters) should return Success when user has auth and data exists`() =
        runTest {
            // Arrange
            val dto: UserDto = mockk(relaxed = true)
            val entity: User = mockk(relaxed = true)
            val dtoList = listOf(dto)
            val entityList = listOf(entity)

            every { permissionService.canPerformAction(any(), any()) } returns true
            every { repository.fetchByQuery(any()) } returns flowOf(Response.Success(dtoList))
            every { validatorService.validateDto(any()) } returns Unit
            every { mapper.toEntity(dto) } returns entity

            // Call
            val result = useCase.execute(queryParams).single()

            // Assertions
            assertEquals(entityList, (result as Response.Success).data)
            verifyOrder {
                permissionService.canPerformAction(user, requiredPermission)
                repository.fetchByQuery(queryParams)
                validatorService.validateDto(dto)
                mapper.toEntity(dto)
            }
        }

    @Test
    fun `execute(QueryParameters) should return Empty when the repository doesn't find data`() =
        runTest {
            // Arrange
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { repository.fetchByQuery(any()) } returns flowOf(Response.Empty)

            // Call
            val result = useCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verifyOrder {
                permissionService.canPerformAction(user, requiredPermission)
                repository.fetchByQuery(queryParams)
            }
        }

    @Test
    fun `execute(QueryParameters) should throw UnauthorizedAccessException when the user has no auth`() =
        runTest {
            // Arrange
            every { permissionService.canPerformAction(any(), any()) } returns false

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(queryParams).single()
            }

            // Assertions
            verify {
                permissionService.canPerformAction(user, requiredPermission)
            }
        }

}
