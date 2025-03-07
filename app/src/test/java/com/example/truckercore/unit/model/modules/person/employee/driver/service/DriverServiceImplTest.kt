package com.example.truckercore.unit.model.modules.person.employee.driver.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.modules.person.employee.driver.service.DriverService
import com.example.truckercore.modules.person.employee.driver.service.DriverServiceImpl
import com.example.truckercore.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class DriverServiceImplTest : KoinTest {

    private val getDriver: GetDriverUseCase by inject()
    private val getPerson: GetPersonWithDetailsUseCase by inject()
    private val service: DriverService by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val driver: Driver = mockk()
    private val personWD: PersonWithDetails = mockk()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetDriverUseCase> { mockk() }
                        single<GetPersonWithDetailsUseCase> { mockk() }
                        single<DriverService> { DriverServiceImpl(mockk(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    //----------------------------------------------------------------------------------------------
    // Testing fetchDriver(documentParam: DocumentParameters)
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return Driver when the result is successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any() as DocumentParameters) } returns flowOf(
                Response.Success(
                    driver
                )
            )

            // Call
            val result = service.fetchDriver(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(driver, (result as Response.Success).data)
        }

    @Test
    fun `should return Empty when the result is not successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any() as DocumentParameters) } returns flowOf(Response.Empty)

            // Call
            val result = service.fetchDriver(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    //----------------------------------------------------------------------------------------------
    // Testing "fetchDriverWithDetails(documentParam: DocumentParameters)"
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return PersonWithDetail Response when all results are successful`() = runTest {
        // Arrange
        every {
            getPerson.execute(any() as DocumentParameters, PersonCategory.DRIVER)
        } returns flowOf(Response.Success(personWD))

        // Call
        val result = service.fetchDriverWithDetails(docParams).single()

        // Assertions
        assertTrue(result is Response.Success)
        assertEquals(personWD, (result as Response.Success).data)
        verify { getPerson.execute(docParams, PersonCategory.DRIVER) }
    }

}