package com.example.truckercore.model.infrastructure.data_source.firebase.data

import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceErrorMapper
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.DataSourceException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InterpreterException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.MappingException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.UnknownException
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.exceptions.SpecificationException
import com.google.firebase.FirebaseNetworkException

class FirestoreDataSourceErrorMapper : DataSourceErrorMapper {

    override operator fun invoke(e: Throwable, spec: Specification<*>): DataSourceException {
        val dataException = when (e) {
            is InvalidDataException -> InvalidDataException(
                "An error occurred while attempting to recover valid data. Please verify the" +
                        " data source and ensure that the data format is correct: $spec"
            )

            is MappingException -> MappingException(
                "Data mapping failed during the conversion from API response to domain model: $spec."
            )

            is SpecificationException -> InterpreterException(
                "An error occurred while interpreting the: $spec",
                cause = e
            )

            is FirebaseNetworkException -> NetworkException(
                message = "Network connectivity issue encountered while interacting with " +
                        "DataSource: $spec",
                cause = e
            )

            else -> UnknownException(
                message = "An unexpected error occurred on DataSource. Please check the logs" +
                        " for more details: $spec",
                cause = e
            )
        }

        return dataException
    }

}
