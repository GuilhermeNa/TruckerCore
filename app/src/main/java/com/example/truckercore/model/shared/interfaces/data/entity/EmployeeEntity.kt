package com.example.truckercore.model.shared.interfaces.data.entity

import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.FullName

interface EmployeeEntity: Entity {
    val name: FullName
    val email: Email
}