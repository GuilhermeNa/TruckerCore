package com.example.truckercore.model.modules.company.factory

import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules._shared.enums.PersistenceState

object CompanyFactory {

    operator fun invoke(): Company {
        return Company(
            id = CompanyID.generate(),
            persistenceState = PersistenceState.ACTIVE,
            keysRegistry = ValidKeysRegistry()
        )
    }

}