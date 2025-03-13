package com.example.truckercore.unit.model.modules.vip.use_cases

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.vip.dto.VipDto
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.modules.vip.mapper.VipMapper
import com.example.truckercore.model.modules.vip.repository.VipRepository
import com.example.truckercore.model.modules.vip.use_cases.implementations.GetVipUseCaseImpl
import com.example.truckercore.model.modules.vip.use_cases.interfaces.GetVipUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

internal class GetVipUseCaseTest : KoinTest {

    private val permission: PermissionService by inject()
    private val repository: VipRepository by inject()
    private val validator: ValidatorService by inject()
    private val mapper: VipMapper by inject()
    private val getVip: GetVipUseCase by inject()

    private val queryParams: QueryParameters = mockk()

    @BeforeEach
    fun beforeEach() {
        startKoin {
            modules(
                module {
                    single<PermissionService> { mockk() }
                    single<VipRepository> { mockk() }
                    single<ValidatorService> { mockk() }
                    single<VipMapper> { mockk() }
                    single<GetVipUseCase> {
                        GetVipUseCaseImpl(
                            Permission.VIEW_VIP,
                            get(), get(), get(), get()
                        )
                    }
                }
            )
        }
    }

    @AfterEach
    fun afterEach() = stopKoin()

    @Test
    fun `should return a Success response when user has permission and data have been found`() =
        runTest {
            // Arrange
            val userWithPermission: User = mockk(relaxed = true)
            val vip: Vip = mockk()
            val vipDto: VipDto = mockk()
            val successResponse = Response.Success(listOf(vipDto))

            every { permission.canPerformAction(any(), any()) } returns true
            every { queryParams.user } returns userWithPermission
            every { repository.fetchByQuery(queryParams) } returns flowOf(successResponse)
            every { validator.validateDto(any()) } just Runs
            every { mapper.toEntity(any()) } returns vip

            // Call
            val result = getVip.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertTrue(result.data.contains(vip))
            verifyOrder {
                permission.canPerformAction(
                    userWithPermission,
                    Permission.VIEW_VIP
                )
                repository.fetchByQuery(queryParams)
                validator.validateDto(successResponse.data[0])
                mapper.toEntity(successResponse.data[0])
            }
        }

    @Test
    fun `should throw UnauthorizedAccessException when the user have no permission for view vip`() =
        runTest {
            // Arrange
            val userWithoutPermission: User = mockk(relaxed = true)

            every { permission.canPerformAction(any(), any()) } returns false
            every { queryParams.user } returns userWithoutPermission

            // Call
            assertThrows<UnauthorizedAccessException> {
                getVip.execute(queryParams).single()
            }

            // Assertions
            verify(exactly = 1) {
                permission.canPerformAction(
                    userWithoutPermission,
                    Permission.VIEW_VIP
                )
            }
            verify(exactly = 0) { repository.fetchByQuery(any()) }
            verify(exactly = 0) { validator.validateDto(any()) }
            verify(exactly = 0) { mapper.toEntity(any()) }

        }

    @Test
    fun `should return an Empty response when the firebase response is Empty`() =
        runTest {
            // Arrange
            val userWithPermission: User = mockk(relaxed = true)

            every { permission.canPerformAction(any(), any()) } returns true
            every { queryParams.user } returns userWithPermission
            every { repository.fetchByQuery(queryParams) } returns flowOf(Response.Empty)

            // Call
            val result = getVip.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verifyOrder {
                permission.canPerformAction(userWithPermission, Permission.VIEW_VIP)
                repository.fetchByQuery(queryParams)
            }
            verify(exactly = 0) { validator.validateDto(any()) }
            verify(exactly = 0) { mapper.toEntity(any()) }
        }

    @Test
    fun `should return an Empty response when the firebase response is Error`() =
        runTest {
            // Arrange
            val userWithPermission: User = mockk(relaxed = true)

            every { permission.canPerformAction(any(), any()) } returns true
            every { queryParams.user } returns userWithPermission
            every { repository.fetchByQuery(queryParams) } returns flowOf(Response.Error(mockk()))

            // Call
            val result = getVip.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verifyOrder {
                permission.canPerformAction(userWithPermission, Permission.VIEW_VIP)
                repository.fetchByQuery(queryParams)
            }
            verify(exactly = 0) { validator.validateDto(any()) }
            verify(exactly = 0) { mapper.toEntity(any()) }
        }

}