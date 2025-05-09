package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.infrastructure.security.contracts.SystemManager
import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.modules._contracts.BaseEntity
import com.example.truckercore.model.shared.enums.Persistence

data class Company(
    override val id: CompanyID,
    override val persistence: Persistence,
    override val validAccessKeys: ValidKeysRegistry,
) : BaseEntity, SystemManager