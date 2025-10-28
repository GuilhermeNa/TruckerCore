package com.example.truckercore.layers.domain.base.contracts.others

interface DomainCollection<T> {

    fun add(item: T)

    fun addAll(items: List<T>)

    fun toList(): List<T>

}