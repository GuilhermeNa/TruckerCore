package com.example.truckercore.modules.user.entity

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime
/**
 * Represents a user in the system. This entity contains information about the user's
 * membership status (VIP), their access permissions, level, and personal category.
 * It also provides methods to check if a user has a specific permission or if their VIP status is active.
 *
 * @property isVip Boolean indicating whether the user has VIP status.
 * @property vipStart The start date of the VIP status. This is nullable as it may not be set if the user is not VIP.
 * @property vipEnd The end date of the VIP status. This is nullable, and used to determine if the VIP status is still valid.
 * @property level The level of the user, indicating the user's rank or access tier in the system.
 * @property permissions A set of permissions assigned to the user, used for access control.
 * @property personFLag The person's category, which helps define the user's classification.
 *
 */
data class User(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val isVip: Boolean,
    val vipStart: LocalDateTime? = null,
    val vipEnd: LocalDateTime? = null,
    val level: Level,
    val permissions: HashSet<Permission> = hashSetOf(),
    val personFLag: PersonCategory
) : Entity {

    /**
     * Checks if the user has the specified permission.
     *
     * @param permission The permission to check for in the user's set of permissions.
     * @return Boolean indicating whether the user has the specified permission.
     */
    fun hasPermission(permission: Permission): Boolean {
        return permissions.contains(permission)
    }

    /**
     * Checks if the user's VIP status is currently active based on the current time and the end date.
     * If the user is a VIP and the VIP status has not yet expired, it returns true.
     * If the user is not a VIP or the VIP status has expired, it returns false.
     *
     * @return Boolean indicating whether the user's VIP status is active.
     */
    fun isVipActive(): Boolean {
        val now = LocalDateTime.now()
        return vipEnd?.let { ve ->
            isVip && ve.isAfter(now)
        } ?: false
    }

}
