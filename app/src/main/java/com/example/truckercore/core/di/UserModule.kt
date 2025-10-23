package com.example.truckercore.core.di

import com.example.truckercore.data.modules.user.manager.UserManager
import com.example.truckercore.data.modules.user.manager.UserManagerImpl
import com.example.truckercore.data.modules.user.use_cases.GetUserUserCase
import com.example.truckercore.data.modules.user.use_cases.GetUserUserCaseImpl
import org.koin.dsl.module

val userModule = module {
    single<GetUserUserCase> { GetUserUserCaseImpl(get()) }

    single<UserManager> { UserManagerImpl() }
}