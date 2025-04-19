package com.example.truckercore.model.infrastructure.integration._data.for_api

import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification

interface DataSourceInterpreter<out R1, out R2> {

    fun interpretIdSearch(spec: Specification<*>): R1

    fun interpretFilterSearch(spec: Specification<*>): R2

}