package com.example.truckercore.unit.model.shared.modules.file.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.modules.file.dto.FileDto
import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.modules.file.mapper.FileMapper
import com.example.truckercore.model.shared.modules.file.repository.FileRepository
import com.example.truckercore.model.shared.modules.file.use_cases.implementations.UpdateFileUseCaseImpl
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.CheckFileExistenceUseCase
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.UpdateFileUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
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
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

class UpdateFileUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: FileRepository by inject()
    private val checkExistence: CheckFileExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: FileMapper by inject()
    private val useCase: UpdateFileUseCase by inject()

    private val id = "fileId"
    private val file: File = mockk()
    private val dto: FileDto = mockk()
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
                        single<FileRepository> { mockk() }
                        single<CheckFileExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<FileMapper> { mockk() }
                        single<UpdateFileUseCase> {
                            UpdateFileUseCaseImpl(
                                Permission.UPDATE_FILE,
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
    fun `execute() should return success when file exists and is updated successfully`() =
        runTest {
            // Arrange
            every { file.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, file).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                file.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_FILE)
                validatorService.validateEntity(file)
                mapper.toDto(file)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the StorageFile id is null`() =
        runTest {
            // Arrange
            every { file.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, file).single()
            }

            // Assertions
            verify {
                file.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { file.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, file).single()
            }

            // Assertions
            verifyOrder {
                file.id
                checkExistence.execute(user, id)
            }
        }

}
