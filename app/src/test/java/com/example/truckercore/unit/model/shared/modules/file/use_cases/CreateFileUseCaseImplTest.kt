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
import com.example.truckercore.shared.modules.file.use_cases.implementations.CreateFileUseCaseImpl
import com.example.truckercore.shared.modules.file.use_cases.interfaces.CreateFileUseCase
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateFileUseCaseImplTest : KoinTest {

    private val requiredPermission: Permission = Permission.CREATE_FILE
    private val permissionService: PermissionService by inject()
    private val repository: FileRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: FileMapper by inject()
    private val useCase: CreateFileUseCase by inject()

    private val user = mockk<User>()
    private val file = mockk<File>()

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
                        single<CreateFileUseCase> {
                            CreateFileUseCaseImpl(
                                Permission.CREATE_FILE,
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
    fun `execute() should return success when file is correctly created`() = runTest {
        // Arrange
        val dto = mockk<FileDto>()
        val id = "newFileObjectId"
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            permissionService.canPerformAction(user, requiredPermission)
            validatorService.validateForCreation(file)
            mapper.toDto(file)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, file).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requiredPermission)
        }
    }

}
