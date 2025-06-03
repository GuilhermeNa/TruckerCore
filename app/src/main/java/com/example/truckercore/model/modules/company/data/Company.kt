package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.infrastructure.security.data.Key
import com.example.truckercore.model.infrastructure.security.contracts.SystemManager
import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.modules._shared._contracts.entity.BaseEntity
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class Company(
    override val id: CompanyID,
    override val persistenceState: PersistenceState,
    override val keysRegistry: ValidKeysRegistry
) : BaseEntity<Company>, SystemManager {

    val keysValue get() = keysRegistry.dataValue
    val idVal get() = id.value

    @InternalUseOnly
    override fun copyWith(persistence: PersistenceState): Company {
        return copy(persistenceState = persistence)
    }

    fun registerKey(key: Key) {
        keysRegistry.registerKey(key)
    }

}