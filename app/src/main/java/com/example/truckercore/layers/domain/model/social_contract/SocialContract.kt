package com.example.truckercore.layers.domain.model.social_contract

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.Document
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.SocialContractID
import com.example.truckercore.layers.domain.base.others.Period

data class SocialContract(
    override val id: SocialContractID,
    override val url: Url,
    override val companyId: CompanyID,
    override val status: Status,
    override val period: Period
): Document
