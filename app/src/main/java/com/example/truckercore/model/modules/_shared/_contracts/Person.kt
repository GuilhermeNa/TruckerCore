package com.example.truckercore.model.modules._shared._contracts

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.FullName

interface Person {
    val email: Email?
    val name: FullName
}