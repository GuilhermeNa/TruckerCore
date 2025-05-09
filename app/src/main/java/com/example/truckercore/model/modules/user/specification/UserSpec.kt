package com.example.truckercore.model.modules.user.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.filters.WhereEqual
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data.UserID

data class UserSpec(
    override val entityId: UserID? = null,
    val companyId: CompanyID? = null,
    val category: Role? = null,
    val uid: UID? = null,
) : Specification<UserDto> {

    override val dtoClass = UserDto::class.java

    override val collection = Collection.USER

    override fun getFilters(): List<Filter> {
        val ml = mutableListOf<Filter>()

        uid?.let { ml.add(WhereEqual(Field.UID, it.value)) }
        companyId?.let { ml.add(WhereEqual(Field.COMPANY_ID, it.value)) }
        category?.let { ml.add(WhereEqual(Field.CATEGORY, it)) }

        return ml
    }

}
