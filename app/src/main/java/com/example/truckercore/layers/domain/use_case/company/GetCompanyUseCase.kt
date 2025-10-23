package com.example.truckercore.layers.domain.use_case.company

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.data.base.specification.specs_impl.CompanySpec

interface GetCompanyUseCase {

    suspend operator fun invoke(spec: CompanySpec): DataOutcome<Company>

}

class GetCompanyUseCaseImpl(private val dataRepository: DataRepository) : GetCompanyUseCase {

    override suspend fun invoke(spec: CompanySpec): DataOutcome<Company> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { dto -> getSuccessResponse(dto) }

        } catch (e: Exception) {
            e.handleErrorResponse("$UNKNOWN_ERR_MSG $spec")
        }

    private fun getSuccessResponse(dto: CompanyDto): DataOutcome.Success<Company> {
        val company = CompanyMapper.toEntity(dto)
        return DataOutcome.Success(company)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching a Company."
    }

}