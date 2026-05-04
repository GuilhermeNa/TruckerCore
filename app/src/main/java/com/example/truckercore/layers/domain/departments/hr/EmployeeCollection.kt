package com.example.truckercore.layers.domain.departments.hr

import com.example.truckercore.layers.domain.base.contracts.ID
import com.example.truckercore.layers.domain.base.contracts.DomainCollection
import com.example.truckercore.layers.domain.base.contracts.Employee

class EmployeeCollection(
    private val dataSet: MutableSet<Employee> = mutableSetOf()
) : DomainCollection<Employee> {

    override val data: Set<Employee> = dataSet.toSet()

    override fun findBy(id: ID): Employee? =
        dataSet.firstOrNull { employee -> employee.id == id }

    override fun add(item: Employee) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Employee>) {
        dataSet.addAll(items)
    }

    fun contains(id: ID): Boolean = dataSet.any { it.id == id }

    fun clear() {
        dataSet.clear()
    }

}