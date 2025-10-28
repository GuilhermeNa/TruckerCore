package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.ids.UserID

interface Person: Entity {
    val name: Name
    val email: Email
    val userId: UserID?
}