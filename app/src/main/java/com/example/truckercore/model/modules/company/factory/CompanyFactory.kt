package com.example.truckercore.model.modules.company.factory

import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.shared.enums.Persistence

object CompanyFactory {

    operator fun invoke(): Company {
        return Company(
            id = CompanyID.generate(),
            persistence = Persistence.ACTIVE,
            keysRegistry = ValidKeysRegistry()
        )
    }

}