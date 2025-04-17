package com.example.truckercore.model.modules.user

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data_helper.Category
import com.example.truckercore.model.modules.user.data_helper.UserID
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

data class UserSpec(
    val userId: UserID? = null,
    val companyId: CompanyID? = null,
    val permission: Permission? = null,
    val category: Category? = null,
    val level: Level? = null,
    val vipOnly: Boolean? = null,
): Specification<UserDto> {

    override val dtoClass = UserDto::class.java

    override val collection = Collection.USER

    override fun toDocumentReference(baseRef: CollectionReference): DocumentReference {
        val id = userId?.value ?: throw NullPointerException()
        return baseRef.document(id)
    }

    override fun toQuery(baseQuery: Query): Query {
        var base = baseQuery

        userId?.let { base = base.whereEqualTo("id", it.value) }
        companyId?.let { base = base.whereEqualTo("companyId", it.value) }
        permission?.let { base = base.whereArrayContains("permissions", it.name) }
        category?.let { base = base.whereEqualTo("category", it.name) }
        level?.let { base = base.whereEqualTo("level", it.name) }
        vipOnly?.let { base = base.whereEqualTo("vip.active", it) }

        return base
    }

}
