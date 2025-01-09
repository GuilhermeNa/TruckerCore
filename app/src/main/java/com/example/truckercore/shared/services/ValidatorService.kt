package com.example.truckercore.shared.services

import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.ValidatorStrategy

internal class ValidatorService<E : Entity, D : Dto>(private val strategy: ValidatorStrategy<E, D>) {

    fun validateDto(dto: D) {
        strategy.validateDto(dto)
    }

    fun validateEntity(entity: E) {
        strategy.validateEntity(entity)
    }

    fun validateForCreation(entity: E) {
        strategy.validateForCreation(entity)
    }

}