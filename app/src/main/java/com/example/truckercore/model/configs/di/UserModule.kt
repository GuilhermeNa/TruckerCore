package com.example.truckercore.model.configs.di

import com.example.truckercore.model.modules.user.manager.UserManager
import com.example.truckercore.model.modules.user.use_cases.GetUserUserCase
import com.example.truckercore.model.modules.user.use_cases.GetUserUserCaseImpl
import org.koin.dsl.module

val userModule = module {
    single<GetUserUserCase> { GetUserUserCaseImpl(get()) }

    single<UserManager> { UserManagerImpl(get()) }
}