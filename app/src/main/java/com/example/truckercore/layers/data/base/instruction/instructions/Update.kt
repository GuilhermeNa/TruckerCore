package com.example.truckercore.layers.data.base.instruction.instructions

import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.instruction._contracts.Instruction

/**
 * Represents an update instruction within the system.
 *
 * This instruction updates an existing entity identified by the ID contained in [data].
 * Unlike [Put], this instruction assumes that the entity already exists and that only
 * specific attributes or the entire object may be updated, depending on the API-level
 * wrapper implementation.
 *
 * The target collection is resolved dynamically using [CollectionResolver] based on the
 * concrete type of [data].
 *
 * @param data The data transfer object containing updated values.
 */
data class Update(val data: BaseDto) : Instruction {

    override fun getId(): String = data.id ?: throw InfraException.Instruction(
        "ID cannot be null for an update instruction."
    )

    override fun getCollection(): String = CollectionResolver(data).name

}