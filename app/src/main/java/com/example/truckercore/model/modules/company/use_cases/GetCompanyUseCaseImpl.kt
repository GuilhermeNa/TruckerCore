package com.example.truckercore.model.modules.company.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.mapper.CompanyMapper
import com.example.truckercore.model.modules.company.specification.CompanySpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.mapAppResponse

class GetCompanyUseCaseImpl(
    private val dataRepository: DataRepository
) : GetCompanyUseCase {

    override suspend fun invoke(spec: CompanySpec): AppResponse<Company> {
        return dataRepository.findOneBy(spec).mapAppResponse(
            onSuccess = { getSuccessResponse(it) },
            onEmpty = { AppResponse.Empty },
            onError = { AppResponse.Error(it) }
        )
    }

    private fun getSuccessResponse(dto: CompanyDto): AppResponse.Success<Company> {
        val company = CompanyMapper.toEntity(dto)
        return AppResponse.Success(company)
    }


}