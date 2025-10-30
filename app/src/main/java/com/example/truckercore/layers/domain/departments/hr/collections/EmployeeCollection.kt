package com.example.truckercore.layers.domain.departments.hr.collections

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection
import com.example.truckercore.layers.domain.base.contracts.others.Employee

class EmployeeCollection(
    private val dataSet: MutableSet<Employee> = mutableSetOf()
) : DomainCollection<Employee> {

    override fun add(item: Employee) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Employee>) {
        dataSet.addAll(items)
    }

    override fun toList(): List<Employee> = dataSet.toList()



}