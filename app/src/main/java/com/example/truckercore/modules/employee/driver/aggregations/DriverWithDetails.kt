package com.example.truckercore.modules.employee.driver.aggregations

import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile

data class DriverWithDetails(
    val driver: Driver,
    val photo: StorageFile? = null,
    val personalData: Set<PersonalData>? = emptySet()
)
