package com.example.truckercore.model.modules.auditable

import com.example.truckercore.model.modules.auditable.data.Audit
import com.example.truckercore.model.shared.interfaces.data.entity.Entity

data class Auditable<T: Entity>(
    val entity: T,
    val audit: Audit
)