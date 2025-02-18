package com.example.truckercore.unit.modules.fleet.truck.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.service.TruckService
import com.example.truckercore.modules.fleet.truck.service.TruckServiceImpl
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
import org.junit.jupiter.api.BeforeAll
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

class TruckServiceImplTest : KoinTest {

    private val getTruck: GetTruckUseCase by inject()
    private val getTruckWithDetailsUseCase: AggregateTruckWithDetailsUseCase by inject()
    private val truckService: TruckService by inject()

    private val documentParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    private val truck: Truck = mockk()
    private val truckWithDetails: TruckWithDetails = mockk()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetTruckUseCase> { mockk() }
                        single<AggregateTruckWithDetailsUseCase> { mockk() }
                        single<TruckService> { TruckServiceImpl(mockk(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `fetchTruck(DocumentParameters) should call getTruck useCase`() =
        runTest {
            // Arrange
            every { getTruck.execute(documentParams) } returns flowOf(Response.Success(truck))

            // Call
            truckService.fetchTruck(documentParams).single()

            // Assertions
            verify { getTruck.execute(documentParams) }
        }

    @Test
    fun `fetchTruck(QueryParameters) should call getTruck useCase`() =
        runTest {
            // Arrange
            every { getTruck.execute(queryParams) } returns flowOf(Response.Success(listOf(truck)))

            // Call
            truckService.fetchTruck(queryParams).single()

            // Assertions
            verify { getTruck.execute(queryParams) }
        }

    @Test
    fun `fetchTruckWithDetails(DocumentParameters) should call getTruckWithDetailsUseCase`() =
        runTest {
            // Arrange
            every { getTruckWithDetailsUseCase.execute(documentParams) } returns flowOf(
                Response.Success(truckWithDetails)
            )

            // Call
            truckService.fetchTruckWithDetails(documentParams).single()

            // Assertions
            verify { getTruckWithDetailsUseCase.execute(documentParams) }
        }

    @Test
    fun `fetchTruckWithDetails(QueryParameters) should call getTruckWithDetailsUseCase`() =
        runTest {
            // Arrange
            every { getTruckWithDetailsUseCase.execute(queryParams) } returns flowOf(
                Response.Success(listOf(truckWithDetails))
            )

            // Call
            truckService.fetchTruckWithDetails(queryParams).single()

            // Assertions
            verify { getTruckWithDetailsUseCase.execute(queryParams) }
        }

}