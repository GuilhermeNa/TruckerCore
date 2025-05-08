package com.example.truckercore.model.infrastructure.security.contracts

import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.modules.company.data.Key

interface SystemManager {

    val validAccessKeys: ValidKeysRegistry

    fun isKeyValid(key: Key): Boolean {
        return validAccessKeys.contains(key)
    }

}

