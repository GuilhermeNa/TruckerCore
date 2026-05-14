package com.example.truckercore.layers.domain.model.user

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.contracts.Entity
import com.example.truckercore.layers.domain.base.contracts.Person
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.domain.model.admin.Admin
import com.example.truckercore.layers.domain.model.driver.Driver

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
            // Validate domain rules for the provided aggregates.
            checkFK(access.userId)
            checkFK(person.userId!!)
        }

        fun update(role: Role): User {
            validateRoleUpdate(role)
            val newAccess = access.update(role)
            return copy(access = newAccess)
        }

        private fun validateRoleUpdate(role: Role) {
            if (person is Driver && role == Role.AUTONOMOUS) {
                throw DomainException.RuleViolation(
                    "Drivers cannot be assigned the AUTONOMOUS role."
                )
            }

            if (person is Driver && role == Role.ADMIN) {
                throw DomainException.RuleViolation(
                    "Drivers cannot be assigned the ADMIN role."
                )
            }

            if (person is Admin && role == Role.DRIVER) {
                throw DomainException.RuleViolation(
                    "Administrators cannot be assigned the DRIVER role."
                )
            }
        }

    }