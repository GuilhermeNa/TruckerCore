package com.example.truckercore.unit.model.infrastructure.data_source.firebase.data

import com.example.truckercore._test_data_provider.TestSpecificationProvider
import com.example.truckercore._test_utils.mockStaticTextUtil
import com.example.truckercore.data.infrastructure.data_source.data.impl.FirestoreDataSourceErrorMapperImpl
import com.example.truckercore.data.infrastructure.data_source.data.errors.data_source_exceptions.InterpreterException
import com.example.truckercore.data.data_source.data.errors.data_source_exceptions.InvalidDataException
import com.example.truckercore.data.infrastructure.data_source.data.errors.data_source_exceptions.MappingException
import com.example.truckercore.data.infrastructure.data_source.data.errors.data_source_exceptions.NetworkException
import com.example.truckercore.data.infrastructure.data_source.data.errors.data_source_exceptions.UnknownException
import com.example.truckercore.data.infrastructure.repository.data.abstractions.SpecificationException
import com.google.firebase.FirebaseNetworkException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirestoreUserInputUserInputCriticalShowMessageMappingDtoToEntityTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val provider = TestSpecificationProvider()
    private val mapper = FirestoreDataSourceErrorMapperImpl()
    private val spec = provider.specMock()

    @BeforeEach
    fun setup() {
        mockStaticTextUtil()
    }

    //----------------------------------------------------------------------------------------------
    // InvalidDataException
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should map InvalidDataException`() {
        val ex =
            com.example.truckercore.data.data_source.data.errors.data_source_exceptions.InvalidDataException(
                "invalid data"
            )

        val result = mapper.invoke(ex, spec)

        assertTrue(result is com.example.truckercore.data.data_source.data.errors.data_source_exceptions.InvalidDataException)
        assertNull(result.cause)
        assertEquals(
            "An error occurred while attempting to recover valid data. Please verify the" +
                    " data source and ensure that the data format is correct: $spec",
            result.message
        )
    }

    //----------------------------------------------------------------------------------------------
    // MappingException
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should map MappingException`() {
        val ex = MappingException("mapping failure")

        val result = mapper.invoke(ex, spec)

        assertTrue(result is MappingException)
        assertNull(result.cause)
        assertEquals(
            "Data mapping failed during the conversion from API response to domain model: $spec.",
            result.message
        )
    }

    //----------------------------------------------------------------------------------------------
    // SpecificationException
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should map SpecificationException to InterpreterException`() {
        val ex = SpecificationException("spec failed")

        val result = mapper.invoke(ex, spec)

        assertTrue(result is InterpreterException)
        assertTrue(result.cause is SpecificationException)
        assertEquals(
            "An error occurred while interpreting the: $spec",
            result.message
        )
        assertEquals(ex, result.cause)
    }

    //----------------------------------------------------------------------------------------------
    // FirebaseNetworkException
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should map FirebaseNetworkException to NetworkException`() {
        val ex = FirebaseNetworkException("no connection")

        val result = mapper.invoke(ex, spec)

        assertTrue(result is NetworkException)
        assertTrue(result.cause is FirebaseNetworkException)
        assertEquals(
            "Network connectivity issue encountered while interacting with DataSource: $spec",
            result.message
        )
        assertEquals(ex, result.cause)
    }

    //----------------------------------------------------------------------------------------------
    // UnknownException
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should map unknown exceptions to UnknownException`() {
        val ex = RuntimeException("unexpected")

        val result = mapper.invoke(ex, spec)

        assertTrue(result is UnknownException)
        assertTrue(result.cause is RuntimeException)
        assertEquals(
            "An unexpected error occurred on DataSource. Please check the logs for more details: $spec",
            result.message
        )
        assertEquals(ex, result.cause)
    }

}