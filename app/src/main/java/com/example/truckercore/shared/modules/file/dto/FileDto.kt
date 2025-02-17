package com.example.truckercore.shared.modules.file.dto

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

internal data class FileDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val parentId: String? = null,
    val url: String? = null,
    @field:JvmField
    val isUpdating: Boolean? = null
) : Dto {

    override fun initializeId(newId: String) = this.copy(
        id = newId,
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

}
