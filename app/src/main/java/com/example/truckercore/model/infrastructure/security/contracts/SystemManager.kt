package com.example.truckercore.model.infrastructure.security.contracts

import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.infrastructure.security.data.Key

interface SystemManager {

    val keysRegistry: ValidKeysRegistry

    fun isKeyValid(key: Key): Boolean {
        return keysRegistry.contains(key)
    }

}

