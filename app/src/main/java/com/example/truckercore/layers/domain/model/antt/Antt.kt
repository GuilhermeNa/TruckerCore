package com.example.truckercore.layers.domain.model.antt

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.Document
import com.example.truckercore.layers.domain.base.enums.AnttCategory
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.AnttID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.others.Period
import com.example.truckercore.layers.domain.base.others.Rntrc

data class Antt(
    override val id: AnttID,
    override val url: Url,
    override val companyId: CompanyID,
    override val status: Status,
    override val period: Period,
    val category: AnttCategory,
    val number: Rntrc
): Document
