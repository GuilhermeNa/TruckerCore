package com.example.truckercore.shared.interfaces

internal interface Mapper<E, D> {
    fun toDto(entity: E): D
    fun toEntity(dto: D): E
}