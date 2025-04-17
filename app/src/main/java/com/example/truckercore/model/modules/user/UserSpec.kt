package com.example.truckercore.model.modules.user

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.configs.constants.Field
import com.example.truckercore.model.infrastructure.integration.specification.Specification
import com.example.truckercore.model.infrastructure.integration.specification.exceptions.NullSpecificationProperty
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
    val category: Category? = null
): Specification<UserDto> {

    override val dtoClass = UserDto::class.java

    override val collection = Collection.USER

    override fun toDocumentReference(baseRef: CollectionReference): DocumentReference {
        val id = userId?.value ?: throw NullSpecificationProperty("UserId is required to create a document reference.")
        return baseRef.document(id)
    }

    override fun toQuery(baseQuery: Query): Query {
        var base = baseQuery

        userId?.let { base = base.whereEqualTo(Field.ID.name, it.value) }
        companyId?.let { base = base.whereEqualTo(Field.COMPANY_ID.name, it.value) }
        category?.let { base = base.whereEqualTo(Field.CATEGORY.name, it.name) }

        return base
    }

}
