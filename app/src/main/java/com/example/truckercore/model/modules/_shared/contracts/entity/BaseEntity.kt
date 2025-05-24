package com.example.truckercore.model.modules._shared.contracts.entity

interface BaseEntity<T>: Persistable<T> {
    val id: ID
}