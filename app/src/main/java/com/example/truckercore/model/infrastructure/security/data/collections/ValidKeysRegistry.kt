package com.example.truckercore.model.infrastructure.security.data.collections

import com.example.truckercore.model.modules.company.data.Key

class ValidKeysRegistry(private val _data: Set<Key>) {

    val dataValue get() = _data.mapTo(mutableSetOf()) { it.value }

    fun contains(key: Key) = _data.contains(key)

    companion object {
        fun from(stringKeys: Set<String>): ValidKeysRegistry {
            val keys = stringKeys.mapTo(mutableSetOf()) { Key(it) }
            return ValidKeysRegistry(_data = keys)
        }
    }

}