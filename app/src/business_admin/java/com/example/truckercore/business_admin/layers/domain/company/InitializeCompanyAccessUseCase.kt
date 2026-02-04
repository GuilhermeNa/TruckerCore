package com.example.truckercore.business_admin.layers.domain.company

import com.example.truckercore.layers.domain.base.ids.UID

interface InitializeCompanyAccessUseCase {

    operator fun invoke(uid: UID)

}

class InitializeCompanyAccessUseCaseImpl: InitializeCompanyAccessUseCase {

    override fun invoke(uid: UID) {
        TODO("Not yet implemented")
    }

}