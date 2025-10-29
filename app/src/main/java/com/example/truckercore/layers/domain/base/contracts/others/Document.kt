package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.others.Period
import java.time.LocalDate

interface Document : DateRange {

    val url: Url

}