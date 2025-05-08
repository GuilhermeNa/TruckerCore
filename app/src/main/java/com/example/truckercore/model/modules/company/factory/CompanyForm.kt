package com.example.truckercore.model.modules.company.factory

import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry

data class CompanyForm(
    val validAccessKey: ValidKeysRegistry
)
