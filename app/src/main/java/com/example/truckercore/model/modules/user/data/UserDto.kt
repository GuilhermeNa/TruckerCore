package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.infrastructure.security.data.ProfileDto
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class UserDto(
    val uid: String? = null,
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    val profile: ProfileDto? = null
) : Dto {

}
