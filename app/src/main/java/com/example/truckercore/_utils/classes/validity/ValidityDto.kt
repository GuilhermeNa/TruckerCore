package com.example.truckercore._utils.classes.validity

import java.time.LocalDate
import java.util.Date

data class ValidityDto(
    val fromDate: Date? = null,
    val toDate: Date ? = null
)
