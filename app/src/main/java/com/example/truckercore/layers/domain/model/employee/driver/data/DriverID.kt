package com.example.truckercore.layers.domain.model.employee.driver.data

@JvmInline
value class DriverID(override val value: String):
    com.example.truckercore.domain._shared._contracts.entity.ID {

    init {
        validate()
    }

    companion object {
        fun generate() = DriverID(com.example.truckercore.domain._shared._contracts.entity.ID.generate())
    }

}