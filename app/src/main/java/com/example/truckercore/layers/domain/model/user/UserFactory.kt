package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID

object UserFactory {

    fun toDraft(uid: UID, userId: UserID, companyID: CompanyID) =
        UserDraft(
            uid = uid,
            id = userId,
            companyId = companyID,
            status = Status.ACTIVE
        )

}