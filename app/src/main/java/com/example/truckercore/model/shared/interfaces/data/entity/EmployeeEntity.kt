package com.example.truckercore.model.shared.interfaces.data.entity

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName

interface EmployeeEntity: Entity {
    val name: FullName
    val email: Email
}