package com.example.truckercore.model.modules.company.use_cases

import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.specification.CompanySpec
import com.example.truckercore._shared.classes.AppResponse

interface GetCompanyUseCase {

    suspend operator fun invoke(spec: CompanySpec): AppResponse<Company>

}