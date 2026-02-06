package com.example.truckercore.layers.domain.model.admin

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.AdminID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID

object AdminFactory {

    operator fun invoke(
        companyId: CompanyID,
        name: Name,
        email: Email,
        userId: UserID
    ) = Admin(
        id = AdminID.generate(),
        companyId = companyId,
        status = Status.ACTIVE,
        name = name,
        email = email,
        userId = userId
    )

}