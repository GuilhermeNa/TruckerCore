package com.example.truckercore.shared.modules.storage_file.dtos

import com.example.truckercore.shared.modules.storage_file.entities.StorageFile
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

internal data class StorageFileDto(
     override val masterUid: String? = null,
     override val id: String? = null,
     override val lastModifierId: String? = null,
     override val creationDate: Date? = null,
     override val lastUpdate: Date? = null,
     override val persistenceStatus: String? = null,
     val parentId: String? = null,
     val url: String? = null,
     val isUpdating: Boolean? = null
) : Dto {

    override fun initializeId(newId: String) = this.copy(
        id = newId,
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

}
