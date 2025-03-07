package com.example.truckercore.unit.model.modules.fleet.truck.use_cases

import android.app.DownloadManager.Query
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.AggregateTrailerWithDetailsUseCase
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.use_cases.implementations.AggregateTruckWithDetailsUseCaseImpl
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.AggregateTruckWithDetailsUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.GetTruckUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
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

class AggregateTruckWithDetailsUseCaseImplTest : KoinTest {

    private val getTruck: GetTruckUseCase by inject()
    private val getTrailerWithDetails: AggregateTrailerWithDetailsUseCase by inject()
    private val getLicensingWithFiles: AggregateLicensingWithFilesUseCase by inject()
    private val aggregateTruckWithDetailsUseCase: AggregateTruckWithDetailsUseCase by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    private val truck: Truck = mockk(relaxed = true)
    private val trailerWithDetails: TrailerWithDetails = mockk(relaxed = true)
    private val licensingWithFile: LicensingWithFile = mockk(relaxed = true)

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetTruckUseCase> { mockk() }
                        single<AggregateTrailerWithDetailsUseCase> { mockk() }
                        single<AggregateLicensingWithFilesUseCase> { mockk() }
                        single<AggregateTruckWithDetailsUseCase> {
                            AggregateTruckWithDetailsUseCaseImpl(get(), get(), get())
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
    fun `execute(DocumentParameters) should call getTruck, getTrailerWithDetails and getLicensingWithFiles useCases`() =
        runTest {
            // Arrange
            every { getTruck.execute(any() as DocumentParameters) } returns flowOf(
                Response.Success(truck)
            )
            every { getTrailerWithDetails.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(trailerWithDetails))
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(truck, successData.truck)
            assertEquals(trailerWithDetails, successData.trailersWithDetails.first())
            assertEquals(licensingWithFile, successData.licensingWithFiles.first())

            // Verify if the use cases are called
            verify { getTruck.execute(docParams) }
            verify { getTrailerWithDetails.execute(any() as QueryParameters) }
            verify { getLicensingWithFiles.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(DocumentParameters) should return Empty when getTruck fails`() =
        runTest {
            // Arrange
            every { getTruck.execute(docParams) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)

            // Verify that getTrailerWithDetails and getLicensingWithFiles are not called
            verify { getTruck.execute(docParams) }
            verify { getTrailerWithDetails.execute(any() as QueryParameters) }
            verify { getLicensingWithFiles.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(DocumentParameters) should return empty trailers when getTrailerWithDetails returns Empty`() =
        runTest {
            // Arrange
            every { getTruck.execute(any() as DocumentParameters) } returns flowOf(
                Response.Success(truck)
            )
            every { getTrailerWithDetails.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(truck, successData.truck)
            assertTrue(successData.trailersWithDetails.isEmpty())
            assertEquals(licensingWithFile, successData.licensingWithFiles.first())

            // Verify if the use cases are called
            verify { getTruck.execute(docParams) }
            verify { getTrailerWithDetails.execute(any() as QueryParameters) }
            verify { getLicensingWithFiles.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(DocumentParameters) should return empty licensing when getLicensingWithFiles returns Empty`() =
        runTest {
            // Arrange
            every { getTruck.execute(docParams) } returns flowOf(Response.Success(truck))
            every { getTrailerWithDetails.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(trailerWithDetails))
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(truck, successData.truck)
            assertEquals(trailerWithDetails, successData.trailersWithDetails.first())
            assertTrue(successData.licensingWithFiles.isEmpty())

            // Verify if the use cases are called
            verify { getTruck.execute(docParams) }
            verify { getTrailerWithDetails.execute(any() as QueryParameters) }
            verify { getLicensingWithFiles.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(QueryParameters) should call getTruck, getTrailerWithDetails and getLicensingWithFiles useCases`() =
        runTest {
            // Arrange
            every { getTruck.execute(queryParams) } returns flowOf(Response.Success(listOf(truck)))
            every { getTrailerWithDetails.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(trailerWithDetails))
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data.first()
            assertEquals(truck, successData.truck)
            assertEquals(trailerWithDetails, successData.trailersWithDetails.first())
            assertEquals(licensingWithFile, successData.licensingWithFiles.first())

            // Verify if the use cases are called
            verify { getTruck.execute(queryParams) }
            verify { getTrailerWithDetails.execute(any() as QueryParameters) }
            verify { getLicensingWithFiles.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(QueryParameters) should return Empty when getTruck fails`() =
        runTest {
            // Arrange
            every { getTruck.execute(queryParams) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)

            // Verify that getTrailerWithDetails and getLicensingWithFiles are not called
            verify(exactly = 1) { getTruck.execute(queryParams) }
            verify(exactly = 0) { getTrailerWithDetails.execute(queryParams) }
            verify(exactly = 0) { getLicensingWithFiles.execute(queryParams) }
        }

    @Test
    fun `execute(QueryParameters) should return Empty when getTrailerWithDetails returns Empty`() =
        runTest {
            // Arrange
            every { getTruck.execute(queryParams) } returns flowOf(Response.Success(listOf(truck)))
            every { getTrailerWithDetails.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(1, successData.size)
            assertEquals(truck, successData.first().truck)
            assertTrue(successData.first().trailersWithDetails.isEmpty())
            assertEquals(licensingWithFile, successData.first().licensingWithFiles.first())

            // Verify if the use cases are called
            verify { getTruck.execute(queryParams) }
            verify { getTrailerWithDetails.execute(any() as QueryParameters) }
            verify { getLicensingWithFiles.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(QueryParameters) should return Empty when getLicensingWithFiles returns Empty`() =
        runTest {
            // Arrange
            every { getTruck.execute(queryParams) } returns flowOf(Response.Success(listOf(truck)))
            every { getTrailerWithDetails.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(trailerWithDetails))
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = aggregateTruckWithDetailsUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(1, successData.size)
            assertEquals(truck, successData.first().truck)
            assertEquals(trailerWithDetails, successData.first().trailersWithDetails.first())
            assertTrue(successData.first().licensingWithFiles.isEmpty())

            // Verify if the use cases are called
            verify { getTruck.execute(queryParams) }
            verify { getTrailerWithDetails.execute(any() as QueryParameters) }
            verify { getLicensingWithFiles.execute(any() as QueryParameters) }
        }

}