package com.example.truckercore.shared.modules.personal_data.dtos

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

internal data class PersonalDataDto(
    override val masterUid: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val parentId: String? = null,
    val name: String? = null,
    val number: String? = null,
    val emissionDate: Date? = null,
    val expirationDate: Date? = null
) : Dto {

    override fun initializeId(newId: String) = this.copy(
        id = newId,
        persistenceStatus = PersistenceStatus.PERSISTED.name,
    )

}