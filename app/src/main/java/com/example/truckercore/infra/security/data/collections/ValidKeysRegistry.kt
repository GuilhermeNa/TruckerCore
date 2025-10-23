package com.example.truckercore.infra.security.data.collections

import com.example.truckercore.infra.security.data.Key

class ValidKeysRegistry(private val _data: MutableSet<Key> = mutableSetOf()) {

    val dataValue get() = _data.mapTo(mutableSetOf()) { it.value }

    fun contains(key: Key) = _data.contains(key)

    fun registerKey(key: Key) {
        _data.add(key)
    }

    companion object {
        fun from(stringKeys: Set<String>): com.example.truckercore.infra.security.data.collections.ValidKeysRegistry {
            val keys = stringKeys.mapTo(mutableSetOf()) {
                Key(
                    it
                )
            }
            return com.example.truckercore.infra.security.data.collections.ValidKeysRegistry(_data = keys)
        }

        fun from(stringKeys: List<String>): com.example.truckercore.infra.security.data.collections.ValidKeysRegistry {
            val keys = stringKeys.mapTo(mutableSetOf()) {
                Key(
                    it
                )
            }
            return com.example.truckercore.infra.security.data.collections.ValidKeysRegistry(_data = keys)
        }

    }

}