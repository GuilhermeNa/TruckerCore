package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.Person
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access

/**
 * Represents a system user.
 *
 * A User is an aggregate root that links authentication/access data
 * with personal information. It belongs to a company and has a visibility
 * status that controls whether it should be considered in default queries.
 */
data class User(
    val uid: UID,
    override val id: UserID,
    override val companyId: CompanyID,
    override val status: Status,
    val access: Access,
    val person: Person
) : Entity {

    init {
        validate()
    }

    /**
     * Validates internal foreign key relationships to ensure
     * domain consistency.
     *
     * - Ensures the Access entity belongs to this User
     * - Ensures the Person entity is linked to this User
     */
    private fun validate() {
        checkFK(access.userId)
        checkFK(person.userId!!)
    }
}