package com.example.truckercore.model.modules.company.factory

import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.shared.enums.Persistence

object CompanyFactory {

    operator fun invoke(): CompanyDto {
        return CompanyDto(
            id = ID.generate(),
            persistence = Persistence.ACTIVE,
            authorizedKeys = null
        )
    }

}