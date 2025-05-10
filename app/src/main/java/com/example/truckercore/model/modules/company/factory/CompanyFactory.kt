package com.example.truckercore.model.modules.company.factory

import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyID

object CompanyFactory {

    operator fun invoke(): Company {
        return Company(
            id = CompanyID(ID.generate())
        )
    }

}