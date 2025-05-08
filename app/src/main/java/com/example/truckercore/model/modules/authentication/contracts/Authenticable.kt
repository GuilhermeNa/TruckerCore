package com.example.truckercore.model.modules.authentication.contracts

import com.example.truckercore.model.modules.authentication.data.UID

interface Authenticable {
    val uid: UID
}