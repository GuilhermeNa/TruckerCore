package com.example.truckercore.model.infrastructure.integration._data.for_api

import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification

interface DataSourceInterpreter {

    operator fun invoke(inst: Specification<*>): Any

}