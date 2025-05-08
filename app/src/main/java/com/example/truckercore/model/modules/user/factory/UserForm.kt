package com.example.truckercore.model.modules.user.factory

import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID

data class UserForm(
    private val companyId: CompanyID,
    private val uid: UID,
    val role: Role
) {

    fun getCompanyId() = companyId.value

    fun getGetUid() = uid.value

}
