package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User

interface SearchParameters {
    val user: User
    val shouldStream: Boolean
}