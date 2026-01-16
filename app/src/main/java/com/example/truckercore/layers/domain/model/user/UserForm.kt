package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.access.Role

data class UserForm(
    val companyId: CompanyID,
    val uid: UID,
    val role: Role
)
