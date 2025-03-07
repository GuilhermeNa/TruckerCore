package com.example.truckercore.unit.shared.modules.storage_file.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.file.dto.FileDto
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.mapper.FileMapper
import com.example.truckercore.shared.modules.file.repository.FileRepository
import com.example.truckercore.shared.modules.file.use_cases.implementations.GetFileUseCaseImpl
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
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

class GetFileUseCaseImplTest : KoinTest {

    private val requiredPermission = Permission.VIEW_FILE
    private val permissionService: PermissionService by inject()
    private val repository: FileRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: FileMapper by inject()
    private val useCase: GetFileUseCase by inject()

    private val user: User = mockk(relaxed = true)
    private val docParams: DocumentParameters = mockk(relaxed = true) {
        every { user } returns this@GetFileUseCaseImplTest.user
    }
    private val queryParams: QueryParameters = mockk(relaxed = true) {
        every { user } returns this@GetFileUseCaseImplTest.user
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
                        single<FileRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<FileMapper> { mockk() }
                        single<GetFileUseCase> {
                            GetFileUseCaseImpl(
                                Permission.VIEW_FILE,
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
    fun `execute(documentParams) should return Success when user has auth and file exists`() =
        runTest {
            // Arrange
            val dto: FileDto = mockk(relaxed = true)
            val entity: File = mockk(relaxed = true)

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
    fun `execute(documentParams) should return Empty when the repository doesn't find data`() =
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
    fun `execute(documentParams) should throw UnauthorizedAccessException when the user has no auth`() =
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
    fun `execute(queryParams) should return Success when user has auth and files exist`() =
        runTest {
            // Arrange
            val dto: FileDto = mockk(relaxed = true)
            val entity: File = mockk(relaxed = true)
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
    fun `execute(queryParams) should return Empty when the repository doesn't find data`() =
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
    fun `execute(queryParams) should throw UnauthorizedAccessException when the user has no auth`() =
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
