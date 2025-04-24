package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.user.data_helper.Category
import com.example.truckercore.model.modules.user.data_helper.UserID
import com.example.truckercore.model.modules.vip.data.Vip
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.entity.Entity

data class User(
    override val id: UserID,
    override val companyId: CompanyID,
    override val persistence: Persistence,
    val permissions: HashSet<Permission>,
    val category: Category,
    val level: Level,

    // Composition Objects
    /*val person: Person,*/
    val vip: Vip

) : Entity {

    fun hasPermission(permission: Permission): Boolean {
        return permissions.contains(permission)
    }

}
