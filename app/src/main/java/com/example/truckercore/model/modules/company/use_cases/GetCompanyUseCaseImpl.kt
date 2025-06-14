package com.example.truckercore.model.modules.company.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.mapper.CompanyMapper
import com.example.truckercore.model.modules.company.specification.CompanySpec
import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.expressions.getOrElse
import com.example.truckercore._shared.expressions.handleErrorResponse

class GetCompanyUseCaseImpl(
    private val dataRepository: DataRepository
) : GetCompanyUseCase {

    override suspend fun invoke(spec: CompanySpec): AppResponse<Company> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { dto -> getSuccessResponse(dto) }

        } catch (e: Exception) {
            e.handleErrorResponse("$UNKNOWN_ERR_MSG $spec")
        }

    private fun getSuccessResponse(dto: CompanyDto): AppResponse.Success<Company> {
        val company = CompanyMapper.toEntity(dto)
        return AppResponse.Success(company)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching a Company."
    }

}