package com.example.truckercore.modules.employee.admin.aggregations

import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile

data class AdminWithDetails(
    val admin: Admin,
    val photo: File? = null,
    val personalDataWithFile: Set<PersonalDataWithFile>? = emptySet()
)
