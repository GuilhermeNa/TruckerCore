package com.example.truckercore.layers.domain.use_case.company

import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data.EditBusinessView
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.base.others.Cnpj
import com.example.truckercore.layers.domain.base.others.CompanyName
import com.example.truckercore.layers.domain.base.others.MunicipalRegistration
import com.example.truckercore.layers.domain.base.others.StateRegistration
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.model.company.CompanyOptional
import java.time.LocalDate

interface CompleteCompanyRegistration {

    suspend operator fun invoke(data: EditBusinessView, company: Company): OperationOutcome

}

class CompleteCompanyRegistrationImpl(
    private val repository: CompanyRepository
) : CompleteCompanyRegistration {

    override suspend fun invoke(data: EditBusinessView, company: Company): OperationOutcome {
        val optionalFields = toOptional(data)
        val newCompany = company.completeRegistration(optionalFields)
        return repository.save(newCompany)
    }

    private fun toOptional(data: EditBusinessView): CompanyOptional {
        return with(data) {
            CompanyOptional(
                cnpj = if (cnpj.isBlank()) null
                else Cnpj(cnpj),

                name = if (name.isBlank()) null
                else CompanyName(name),

                stateRegistration = if (stateReg.isBlank()) null
                else StateRegistration(stateReg),

                municipalRegistration =
                if (municipalReg.isBlank()) null
                else MunicipalRegistration(municipalReg),

                opening = if (opening.isBlank()) null
                else LocalDate.of(
                    opening.substring(4, 6).toInt(),
                    opening.substring(2, 4).toInt(),
                    opening.substring(0, 2).toInt()
                )
            )
        }
    }

}