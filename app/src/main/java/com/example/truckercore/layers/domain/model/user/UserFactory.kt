package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.layers.domain.base.contracts.Employee
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access

object UserFactory {

    fun toDraft(uid: UID, userId: UserID, companyID: CompanyID) =
        UserDraft(
            uid = uid,
            id = userId,
            companyId = companyID,
            status = Status.ACTIVE
        )

    fun toEntity(draft: UserDraft, access: Access, employee: Employee) =
        User(
            uid = draft.uid,
            id = draft.id,
            companyId = draft.companyId,
            status = draft.status,
            access = access,
            person = employee
        )


}