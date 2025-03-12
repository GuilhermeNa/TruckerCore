package com.example.truckercore.model.modules.business_central.entity

import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.interfaces.Entity
import java.time.LocalDateTime

/**
 * Represents a Business Central in the system, which serves as the central entity for business-related data.
 * This class contains the core information about the business, such as its unique identifier, creation and modification dates,
 * and other essential business-related attributes.
 *
 * The `BusinessCentral` entity plays a critical role in the system, acting as the representation of a business unit or entity
 * within the system, encompassing key business-specific information.
 * @param keys The number of VIP keys associated with the business central. Each key represents a unique access
 *             granted for the registration of new users who will be able to manage the business data.
 */
data class BusinessCentral(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val authorizedUserIds: HashSet<String>,
    val keys: Int
) : Entity {

    /**
     * Checks if a user has permission to access the system.
     *
     * This method checks if the provided `userId` is present in the set of authorized user IDs,
     * indicating whether the user has access to the system associated with the `BusinessCentral` object.
     *
     * @param userId The identifier of the user whose access is being verified.
     * @return Returns `true` if the `userId` is present in the set of authorized users,
     *         meaning the user has access to the system. Returns `false` otherwise.
     */
    fun userHasSystemAccess(userId: String): Boolean {
        return authorizedUserIds.contains(userId)
    }

}