package com.example.truckercore.model.modules.aggregation.session.data

import com.example.truckercore.model.modules.authentication.contracts.Authenticable
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.Company

data class Session(
    val uid: UID,
    val company: Company,
    val user: Authenticable
)
