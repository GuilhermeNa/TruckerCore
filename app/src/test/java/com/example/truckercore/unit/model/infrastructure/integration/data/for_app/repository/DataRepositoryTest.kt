/*
package com.example.truckercore.unit.model.infrastructure.integration.data.for_app.repository

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore._test_data_provider.fake_objects.FakeSpec
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.DataAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.error_codes.DataFindErrCode
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.error_codes.DataFlowErrCode
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepositoryImpl
import com.example.truckercore.model.shared.utils.expressions.extractData
import com.example.truckercore.model.shared.utils.expressions.extractError
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class DataRepositoryTest : KoinTest {

    private val dataSource: DataSource<*, *> by inject()
    private val repository: DataRepository by inject()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<DataSource<*, *>> { mockk(relaxed = true) }
                    single { DataAppErrorFactory() }
                    single<DataRepository> { DataRepositoryImpl(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing findOneBy
    //----------------------------------------------------------------------------------------------
    @Test
    fun `findOneBy should return Success when data is found`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val mockData = mockk<FakeDto>()

        coEvery { dataSource.findById(spec) } returns mockData

        // Act
        val result = repository.findOneBy(spec)

        // Assert
        assertTrue(result is AppResponse.Success)
        assertEquals(mockData, (result as AppResponse.Success).data)
    }

    @Test
    fun `findOneBy should return Empty when data is null`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()

        coEvery { dataSource.findById(spec) } returns null

        // Act
        val result = repository.findOneBy(spec)

        // Assert
        assertTrue(result is AppResponse.Empty)
    }

    @Test
    fun `findOneBy should return Error when exception occurs`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val ex = NetworkException()

        coEvery { dataSource.findById(spec) } throws ex

        // Act
        val result = repository.findOneBy(spec)

        // Assert
        assertTrue(result is AppResponse.Error)
        assertEquals(DataFindErrCode.Network, (result as AppResponse.Error).exception.errorCode)
    }

    //----------------------------------------------------------------------------------------------
    // Testing findAllBy
    //----------------------------------------------------------------------------------------------
    @Test
    fun `findAllBy should return Success when data is found`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val mockData = mockk<List<FakeDto>>()

        coEvery { dataSource.findAllBy(spec) } returns mockData

        // Act
        val result = repository.findAllBy(spec)

        // Assert
        assertTrue(result is AppResponse.Success)
        assertEquals(mockData, (result as AppResponse.Success).data)
    }

    @Test
    fun `findAllBy should return Empty when data is null`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()

        coEvery { dataSource.findAllBy(spec) } returns null

        // Act
        val result = repository.findAllBy(spec)

        // Assert
        assertTrue(result is AppResponse.Empty)
    }

    @Test
    fun `findAllBy should return Error when exception occurs`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val ex = NetworkException()

        coEvery { dataSource.findAllBy(spec) } throws ex

        // Act
        val result = repository.findAllBy(spec)

        // Assert
        assertTrue(result is AppResponse.Error)
        assertEquals(DataFindErrCode.Network, (result as AppResponse.Error).exception.errorCode)
    }

    //----------------------------------------------------------------------------------------------
    // Testing flowOneBy
    //----------------------------------------------------------------------------------------------
    @Test
    fun `flowOneBy should return Success when data is emitted`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val mockData = mockk<FakeDto>()
        val mockFlow = flow { emit(mockData) }

        coEvery { dataSource.flowOneBy(spec) } returns mockFlow

        // Act
        val result = repository.flowOneBy(spec).single()

        // Assert
        assertTrue(result is AppResponse.Success)
        assertEquals((result as AppResponse.Success).data, mockData)
    }

    @Test
    fun `flowOneBy should return Empty when data is null`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val mockFlow = flow { emit(null) }

        coEvery { dataSource.flowOneBy(spec) } returns mockFlow

        // Act
        val result = repository.flowOneBy(spec).single()

        // Assert
        assertTrue(result is AppResponse.Empty)
    }

    @Test
    fun `flowOneBy should return Error when exception occurs in flow`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val ex = RuntimeException("Unexpected error")
        val mockFlow = callbackFlow<FakeDto> {
            this.close(ex)
            awaitClose()
        }

        coEvery { dataSource.flowOneBy(spec) } returns mockFlow

        // Act
        val result = repository.flowOneBy(spec).single()

        // Assertion
        assertTrue(result is AppResponse.Error)
        assertTrue(result.extractError().errorCode is DataFlowErrCode.Unknown)
    }

    //----------------------------------------------------------------------
    // Testing flowAllBy
    //----------------------------------------------------------------------
    @Test
    fun `flowAllBy should return Success when data is emitted`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val data = mockk<List<FakeDto>>()
        val mockFlow = flow { emit(data) }

        coEvery { dataSource.flowAllBy(spec) } returns mockFlow

        // Act
        val result = repository.flowAllBy(spec).single()

        // Assert
        assertTrue(result is AppResponse.Success)
        assertEquals(result.extractData(), data)
    }

    @Test
    fun `flowAllBy should return Empty when data is null`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val mockFlow = flow { emit(null) }

        coEvery { dataSource.flowAllBy(spec) } returns mockFlow

        // Act
        val result = repository.flowAllBy(spec).single()

        // Assert
        assertTrue(result is AppResponse.Empty)
    }

    @Test
    fun `flowAllBy should return Error when exception occurs in flow`() = runTest {
        // Arrange
        val spec = mockk<FakeSpec>()
        val ex = RuntimeException("Unexpected error")
        val mockFlow = callbackFlow<List<FakeDto>> {
            this.close(ex)
            awaitClose()
        }

        coEvery { dataSource.flowAllBy(spec) } returns mockFlow

        // Act
        val result = repository.flowAllBy(spec).single()

        // Assert
        assertTrue(result is AppResponse.Error)
        assertEquals(DataFlowErrCode.Unknown, result.extractError().errorCode)
    }

}*/
