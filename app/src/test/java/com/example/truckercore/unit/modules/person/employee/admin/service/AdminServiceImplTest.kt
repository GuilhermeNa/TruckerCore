package com.example.truckercore.unit.modules.person.employee.admin.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.service.AdminService
import com.example.truckercore.modules.person.employee.admin.service.AdminServiceImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AdminServiceImplTest : KoinTest {

    private val getAdmin: GetAdminUseCase by inject()
    private val getAdminWithDetails: AggregateAdminWithDetails by inject()
    private val adminService: AdminService by inject()

    private val docParams: DocumentParameters = mockk()
    private val admin: Admin = mockk()
    private val adminWithDetails: com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails =
        mockk()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<ExceptionHandler> { mockk() }
                        single<GetAdminUseCase> { mockk() }
                        single<AggregateAdminWithDetails> { mockk() }
                        single<AdminService> { AdminServiceImpl(get(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `fetchAdmin() should call getAdmin execute`() =
        runTest {
            // Arrange
            every { getAdmin.execute(any() as DocumentParameters) } returns flowOf(
                Response.Success(
                    admin
                )
            )

            // Call
            adminService.fetchAdmin(docParams).single()

            // Assertions
            verify { getAdmin.execute(docParams) }
        }

    @Test
    fun `fetchAdminWithDetails() should call getAdminWithDetails`() =
        runTest {
            // Arrange
            every { getAdminWithDetails.execute(any()) } returns flowOf(
                Response.Success(
                    adminWithDetails
                )
            )

            // Call
            adminService.fetchAdminWithDetails(docParams).single()

            // Assertions
            verify { getAdminWithDetails.execute(docParams) }
        }

}