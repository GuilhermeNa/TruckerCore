package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.shared.enums.Persistence

data class UserDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null,
    val uid: String? = null,
    val profile: Profile? = null
) : Dto {

    override fun copyWith(id: String?) = this.copy(id = id)

}
