package com.example.truckercore.modules.employee.admin.aggregations

import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.shared.abstractions.Employee
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile

data class AdminWithDetails(
    val admin: Admin,
    val photo: StorageFile? = null,
    val personalData: Set<PersonalData>? = emptySet()
)
