package com.example.truckercore.model.infrastructure.integration._data.for_app.specification

import com.example.truckercore.model.configs.constants.Field

interface Filter {
    val field: Field
    val value: Any
}