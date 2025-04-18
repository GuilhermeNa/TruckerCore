package com.example.truckercore.model.modules.company.use_case.create_new_company

import com.example.truckercore.model.modules.company.repository.CompanyRepository

class CreateNewCompanyUseCaseImpl(
    private val repository: CompanyRepository
): CreateNewCompanyUseCase {

    override fun invoke(form: NewCompanyForm) {

    }

}