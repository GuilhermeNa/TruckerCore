package com.example.truckercore.model.modules.company.use_case.create_new_company

interface CreateNewCompanyUseCase {

    operator fun invoke(form: NewCompanyForm)

}