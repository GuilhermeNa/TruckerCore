package com.example.truckercore.model.infrastructure.security.data.collections

import com.example.truckercore.model.modules.company.data.Key

class ValidKeysRegistry(private val data: Set<Key>) {

    fun contains(key: Key) = data.contains(key)

}