package com.example.truckercore.model.modules._shared.contracts

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName

interface Person {
    val email: Email?
    val name: FullName
}