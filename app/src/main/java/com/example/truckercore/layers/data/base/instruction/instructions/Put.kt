package com.example.truckercore.layers.data.base.instruction.instructions

import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.instruction._contracts.Instruction

/**
 * Represents a put (create or replace) instruction within the system.
 *
 * This instruction is responsible for persisting the provided [data] into its
 * corresponding collection. Depending on the underlying storage or API implementation,
 * this operation may create a new entity or overwrite an existing one with the same ID.
 *
 * The target collection is resolved dynamically using [CollectionResolver] based on the
 * concrete type of [data].
 *
 * @param data The data transfer object to be stored.
 */
data class Put(val data: BaseDto) : Instruction {

    override fun getId(): String = data.id ?: throw InfraException.Instruction(
        "ID cannot be null for an update instruction."
    )

    override fun getCollection(): String = CollectionResolver(data).name

}