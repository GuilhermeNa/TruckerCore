package com.example.truckercore.layers.domain.base.others

import com.example.truckercore.core.error.DomainException
import java.time.Year

data class YearModel(
    val year: Year,
    val model: Year
) {

    init {
        validate()
    }

    private fun validate() {
        if (year < model) throw DomainException.RuleViolation(ERROR_MESSAGE)
    }

    private companion object {
        private const val ERROR_MESSAGE =
            "The manufacturing year cannot be earlier than the model year."
    }

}