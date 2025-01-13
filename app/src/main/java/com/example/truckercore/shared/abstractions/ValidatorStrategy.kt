package com.example.truckercore.shared.abstractions

import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.ValidatorStrategyI

internal abstract class ValidatorStrategy : ValidatorStrategyI {

    protected abstract fun processDtoValidationRules(dto: Dto)

    protected abstract fun processEntityValidationRules(entity: Entity)

    protected abstract fun processEntityCreationRules(entity: Entity)

}