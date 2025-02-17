package com.example.truckercore.modules.employee.driver.aggregations

import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile

data class DriverWithDetails(
    val driver: Driver,
    val photo: File? = null,
    val personalDataWithFile: Set<PersonalDataWithFile>? = emptySet()
)
