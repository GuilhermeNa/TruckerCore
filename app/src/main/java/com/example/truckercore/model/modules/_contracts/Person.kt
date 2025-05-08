package com.example.truckercore.model.modules._contracts

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName

interface Person {
    val name: FullName
    val email: Email?
}