package com.example.truckercore.shared.interfaces

internal interface Mapper {

    fun toEntity(dto: Dto): Entity

    fun toDto(entity: Entity): Dto

}