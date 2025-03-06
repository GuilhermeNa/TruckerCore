package com.example.truckercore.unit.modules.person.employee.admin.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.service.AdminService
import com.example.truckercore.modules.person.employee.admin.service.AdminServiceImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.modules.user.enums.PersonCategory
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
    private val getPerson: GetPersonWithDetailsUseCase by inject()
    private val adminService: AdminService by inject()

    private val docParams: DocumentParameters = mockk()
    private val admin: Admin = mockk()

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
                        single<GetPersonWithDetailsUseCase> { mockk() }
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
    fun `fetchAdmin() should call getAdmin execute`() = runTest {
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
    fun `fetchAdminWithDetails() should call getAdminWithDetails`() = runTest {
        // Arrange
        val personWD: PersonWithDetails = mockk()

        every {
            getPerson.execute(any() as DocumentParameters, PersonCategory.ADMIN)
        } returns flowOf(Response.Success(personWD))

        // Call
        adminService.fetchAdminWithDetails(docParams).single()

        // Assertions
        verify { getPerson.execute(docParams, PersonCategory.ADMIN) }
    }

}