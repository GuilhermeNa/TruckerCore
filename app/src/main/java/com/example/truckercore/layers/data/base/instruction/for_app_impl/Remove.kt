package com.example.truckercore.layers.data.base.instruction.for_app_impl

import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.infra.instruction.base.Instruction
import com.example.truckercore.layers.domain.base.contracts.entity.ID

data class Remove(private val id: ID) : Instruction {

    override fun getId(): String = id.value

    override fun getCollection(): String = CollectionResolver(id).name

}