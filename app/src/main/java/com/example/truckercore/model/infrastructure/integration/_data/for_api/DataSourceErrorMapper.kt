package com.example.truckercore.model.infrastructure.integration._data.for_api

import com.example.truckercore.model.infrastructure.integration._data.for_api.exceptions.DataSourceException
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification

interface DataSourceErrorMapper {

    operator fun invoke(e: Throwable, spec: Specification<*>): DataSourceException

}