package com.example.truckercore.layers.domain.base.contracts

interface Optional<T, E: BaseEntity> {

    fun isFilled(): Boolean

    fun completeRegistration(data: T): E

}