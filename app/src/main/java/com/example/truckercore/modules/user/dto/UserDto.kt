package com.example.truckercore.modules.user.dto

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.time.LocalDateTime
import java.util.Date

internal data class UserDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    @field:JvmField
    val isVip: Boolean? = null,
    val vipStart: Date? = null,
    val vipEnd: Date? = null,
    val level: String? = null,
    val permissions: List<String>? = null,
    val personFLag: String? = null
) : Dto {

    override fun initializeId(newId: String) = this.copy(
        id = newId,
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

}
