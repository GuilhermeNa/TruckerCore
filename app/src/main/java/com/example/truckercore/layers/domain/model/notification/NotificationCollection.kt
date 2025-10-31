package com.example.truckercore.layers.domain.model.notification

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection

class NotificationCollection(
    private val dataSet: MutableSet<Notification> = mutableSetOf()
) : DomainCollection<Notification> {

    override val data: Set<Notification> = dataSet.toSet()

    override fun add(item: Notification) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Notification>) {
        dataSet.addAll(items)
    }

    override fun findBy(id: ID): Notification? = dataSet.firstOrNull { it.id == id }

    fun sortedByCreation(): List<Notification> = dataSet.sortedBy { it.creation }

}