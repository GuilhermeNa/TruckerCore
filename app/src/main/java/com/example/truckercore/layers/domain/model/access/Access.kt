package com.example.truckercore.layers.domain.model.access

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.AccessID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID

/**
 * Represents access control data for a user.
 *
 * This entity defines the user's role and status within a company
 * and is used for authorization and permission checks.
 *
 * @param authorized Indicates whether this access is allowed to enter and navigate
 * the system normally. This flag must be validated and granted by an administrator.
 *
 * @param role Represents the access level of this object, determining which
 * features and operations the user is permitted to perform.
 */
data class Access(
    override val id: AccessID,
    override val status: Status,
    override val companyId: CompanyID,
    val authorized: Boolean = false,
    val userId: UserID,
    val role: Role
) : Entity