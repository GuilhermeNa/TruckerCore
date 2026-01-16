package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.base.contracts.entity.ID

interface DomainCollection<T: Any> {

    val data: Set<T>

    fun add(item: T)

    fun addAll(items: List<T>)

    fun findBy(id: ID): T?

}