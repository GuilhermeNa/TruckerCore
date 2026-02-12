package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.layers.domain.base.contracts.entity.Draft
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID

data class UserDraft(
    val uid: UID,
    override val id: UserID,
    override val companyId: CompanyID,
    override val status: Status
): Draft
