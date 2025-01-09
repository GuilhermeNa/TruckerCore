package com.example.truckercore.shared.interfaces

internal interface ValidatorStrategy<E: Entity, D: Dto> {

    fun validateDto(dto: D)

    fun validateEntity(entity: E)

    fun validateForCreation(entity: E)

}