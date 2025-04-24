package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.modules.user.data_helper.Category
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.example.truckercore.model.shared.interfaces.data.dto.Dto

data class UserDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null,
    val permissions: HashSet<String>? = null,
    val category: Category? = null,
    val level: Level? = null,
): Dto {
    override fun copyWith(id: String?): BaseDto {
        TODO("Not yet implemented")
    }
}
