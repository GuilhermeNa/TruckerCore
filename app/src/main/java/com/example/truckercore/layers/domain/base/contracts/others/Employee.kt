package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.base.contracts.entity.EmployeeID

interface Employee : Person {

    override val id: EmployeeID

}