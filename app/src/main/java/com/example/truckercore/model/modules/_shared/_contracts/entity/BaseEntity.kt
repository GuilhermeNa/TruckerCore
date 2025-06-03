package com.example.truckercore.model.modules._shared._contracts.entity

interface BaseEntity<T>: Persistable<T> {
    val id: ID
}