package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.FullName

interface Person {
    val email: Email?
    val name: FullName
}