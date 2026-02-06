package com.example.truckercore.layers.domain.model.company

import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID

object CompanyFactory {

    operator fun invoke() =
        Company(
            id = CompanyID.generate(),
            status = Status.ACTIVE
        )

}