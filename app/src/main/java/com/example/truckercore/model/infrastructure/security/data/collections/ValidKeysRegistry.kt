package com.example.truckercore.model.infrastructure.security.data.collections

import com.example.truckercore.model.infrastructure.security.data.Key

class ValidKeysRegistry(private val _data: MutableSet<Key> = mutableSetOf()) {

    val dataValue get() = _data.mapTo(mutableSetOf()) { it.value }

    fun contains(key: Key) = _data.contains(key)

    fun registerKey(key: Key) {
        _data.add(key)
    }

    companion object {
        fun from(stringKeys: Set<String>): ValidKeysRegistry {
            val keys = stringKeys.mapTo(mutableSetOf()) { Key(it) }
            return ValidKeysRegistry(_data = keys)
        }

        fun from(stringKeys: List<String>): ValidKeysRegistry {
            val keys = stringKeys.mapTo(mutableSetOf()) { Key(it) }
            return ValidKeysRegistry(_data = keys)
        }

    }

}