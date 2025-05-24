package com.example.truckercore.model.shared.value_classes

import com.example.truckercore.model.modules._shared.contracts.entity.ID
import com.example.truckercore.model.shared.value_classes.exceptions.InvalidGenericIdException

@JvmInline
value class GenericID(override val value: String) : ID {

    init {
        if (value.isBlank()) throw InvalidGenericIdException(
            "Generic ID must not be blank. A valid non-empty identifier is required."
        )

    }

}