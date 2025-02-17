package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.UpdatePersonalDataUseCaseImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.UpdatePersonalDataUseCase
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
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class UpdatePersonalDataUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: PersonalDataRepository by inject()
    private val checkExistence: CheckPersonalDataExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: PersonalDataMapper by inject()
    private val useCase: UpdatePersonalDataUseCase by inject()

    private val id = "personalDataId"
    private val pData: PersonalData = mockk()
    private val dto: PersonalDataDto = mockk()
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
                        single<PersonalDataRepository> { mockk() }
                        single<CheckPersonalDataExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<PersonalDataMapper> { mockk() }
                        single<UpdatePersonalDataUseCase> {
                            UpdatePersonalDataUseCaseImpl(
                                get(), get(), get(), get(), get(), Permission.UPDATE_PERSONAL_DATA
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
    fun `execute() should return success when PersonalData exists and is updated successfully`() =
        runTest {
            // Arrange
            every { pData.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, pData).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                pData.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
                validatorService.validateEntity(pData)
                mapper.toDto(pData)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the PersonalData id is null`() =
        runTest {
            // Arrange
            every { pData.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, pData).single()
            }

            // Assertions
            verify {
                pData.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { pData.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, pData).single()
            }

            // Assertions
            verifyOrder {
                pData.id
                checkExistence.execute(user, id)
            }
        }


}
