package com.example.truckercore.shared.modules.personal_data.entity

import com.example.truckercore.shared.modules.storage_file.entities.StorageFile
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

/**
 * This class represents personal documents such as CPF (Brazilian Taxpayer ID), RG (Brazilian Identity Card),
 * driver's license, and other similar documents. It contains the necessary details about the document,
 * including its identifier, name, number, and important dates.
 *
 * @property parentId Refers to the object or entity to which the document belongs (e.g., a person or organization).
 * @property name The name of the document (e.g., "CPF", "RG", "Driver's License", etc.).
 * @property number The number associated with the document (e.g., CPF number, RG number, or driver's license number).
 * @property emissionDate The date when the document was issued or emitted.
 * @property expirationDate The expiration date of the document, indicating when the document will expire.
 * @property file The file that contains the image data of the document.
 */
data class PersonalData(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val parentId: String,
    val name: String,
    val number: String,
    val emissionDate: LocalDateTime,
    val expirationDate: LocalDateTime? = null,
    val file: StorageFile? = null
) : Entity {




}
