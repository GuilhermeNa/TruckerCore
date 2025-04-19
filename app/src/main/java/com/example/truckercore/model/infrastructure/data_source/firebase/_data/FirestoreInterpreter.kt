package com.example.truckercore.model.infrastructure.data_source.firebase._data

import com.example.truckercore.model.infrastructure.integration._data.for_api.DataSourceInterpreter
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification

class FirestoreInterpreter : DataSourceInterpreter {

    override fun invoke(inst: Specification<*>): Any {
        inst.byId()

    }

}