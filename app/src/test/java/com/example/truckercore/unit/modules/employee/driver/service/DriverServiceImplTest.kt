package com.example.truckercore.unit.modules.employee.driver.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.employee.driver.aggregations.DriverWithDetails
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.service.DriverService
import com.example.truckercore.modules.employee.driver.service.DriverServiceImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.AggregateDriverWithDetails
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
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
    private val getDriverWithDetails: AggregateDriverWithDetails by inject()
    private val driverService: DriverService by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val driver: Driver = mockk()
    private val driverWithDetails: DriverWithDetails = mockk()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetDriverUseCase> { mockk() }
                        single<AggregateDriverWithDetails> { mockk() }
                        single<DriverService> { DriverServiceImpl(mockk(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `fetchDriver() should return Driver when the result is successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any()) } returns flowOf(Response.Success(driver))

            // Call
            val result = driverService.fetchDriver(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(driver, (result as Response.Success).data)
        }

    @Test
    fun `fetchDriver() should return Empty when the result is not successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any()) } returns flowOf(Response.Empty)

            // Call
            val result = driverService.fetchDriver(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    @Test
    fun `fetchDriverWithDetails() should return DriverWithDetails when all results are successful`() =
        runTest {
            // Arrange
            every { getDriverWithDetails.execute(any()) } returns flowOf(
                Response.Success(driverWithDetails)
            )

            // Call
            val result = driverService.fetchDriverWithDetails(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(driverWithDetails, (result as Response.Success).data)
        }

    @Test
    fun `fetchDriverWithDetails() should return Empty when DriverWithDetails result is not successful`() =
        runTest {
            // Arrange
            every { getDriverWithDetails.execute(any()) } returns flowOf(Response.Empty)

            // Call
            val result = driverService.fetchDriverWithDetails(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

}