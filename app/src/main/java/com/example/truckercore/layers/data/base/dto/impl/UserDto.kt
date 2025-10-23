package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.data.infrastructure.security.data.ProfileDto
import com.example.truckercore.data.modules._shared.enums.PersistenceState
import com.example.truckercore.layers.data.base.dto.contracts.Dto

data class UserDto(
    val uid: String? = null,
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    val profile: ProfileDto? = null
) : Dto {

}
