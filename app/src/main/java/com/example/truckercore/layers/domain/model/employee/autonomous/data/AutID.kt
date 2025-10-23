package com.example.truckercore.layers.domain.model.employee.autonomous.data

@JvmInline
value class AutID(override val value: String) :
    com.example.truckercore.domain._shared._contracts.entity.ID {

    init {
        validate()
    }

    companion object {
        fun generate() = AutID(com.example.truckercore.domain._shared._contracts.entity.ID.generate())
    }

}