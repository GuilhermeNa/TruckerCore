package com.example.truckercore.model.infrastructure.integration._data.for_app.specification.filters

import com.example.truckercore.model.configs.constants.Field
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Filter

data class WhereIn(
    override val field: Field,
    override val value: Any
): Filter