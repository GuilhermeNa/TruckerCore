package com.example.truckercore.shared.utils.sealeds

import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity

sealed class ValidatorInput {

    data class DtoInput(val dto: Dto): ValidatorInput()

    data class EntityInput(val entity: Entity): ValidatorInput()

}