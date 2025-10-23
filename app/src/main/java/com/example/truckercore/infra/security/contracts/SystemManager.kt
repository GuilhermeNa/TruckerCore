package com.example.truckercore.infra.security.contracts

import com.example.truckercore.infra.security.data.collections.ValidKeysRegistry
import com.example.truckercore.infra.security.data.Key

interface SystemManager {

    val keysCollection: ValidKeysRegistry

    fun isKeyValid(key: Key): Boolean {
        return keysCollection.contains(key)
    }

}

