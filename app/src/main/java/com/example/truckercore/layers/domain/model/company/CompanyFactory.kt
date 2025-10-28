package com.example.truckercore.layers.domain.model.company

import com.example.truckercore.infra.security.data.collections.ValidKeysRegistry
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID

object CompanyFactory {

    operator fun invoke(): Company {
        return Company(
            id = CompanyID.create(),
            status = Status.ACTIVE,
            keysCollection = ValidKeysRegistry()
        )
    }

}