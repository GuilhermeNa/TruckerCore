package com.example.truckercore.shared.interfaces

internal interface NewMapper {

    fun toEntity(dto: Dto): Entity

    fun toDto(entity: Entity): Dto

}