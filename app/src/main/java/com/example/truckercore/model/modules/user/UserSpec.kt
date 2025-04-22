package com.example.truckercore.model.modules.user

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.configs.constants.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.filters.WhereEqual
import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data_helper.Category
import com.example.truckercore.model.modules.user.data_helper.UserID

data class UserSpec(
    override val entityId: UserID? = null,
    val companyId: CompanyID? = null,
    val category: Category? = null,
) : Specification<UserDto> {

    override val dtoClass = UserDto::class.java

    override val collection = Collection.USER

    override fun getFilters(): List<Filter> {
        val ml = mutableListOf<Filter>()

        companyId?.let { ml.add(WhereEqual(Field.COMPANY_ID, it.value)) }
        category?.let { ml.add(WhereEqual(Field.CATEGORY, it)) }

        return ml
    }

}
