package com.example.truckercore.configs.di

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseConverterImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseQueryBuilderImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.NewFireBaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.NewFirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.implementations.NewFirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.user.dto.UserDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.core.qualifier.named
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }
    single { NewFirebaseConverter() }
    single { NewFirebaseQueryBuilder(get()) }
    single<FirebaseQueryBuilder> { FirebaseQueryBuilderImpl(get()) }

    single<NewFireBaseRepository> { NewFireBaseRepositoryImpl(get(), get()) }

    // Repository
    single<FirebaseRepository<BusinessCentralDto>>(named("Repository_BusinessCentral")) {
        FirebaseRepositoryImpl(
            get(),
            get(named("Converter_BusinessCentral")),
            Collection.CENTRAL
        )
    }
    single<FirebaseRepository<LicensingDto>>(named("Repository_Licensing")) {
        FirebaseRepositoryImpl(
            get(),
            get(named("Converter_Licensing")),
            Collection.LICENSING
        )
    }
    single<FirebaseRepository<UserDto>>(named("Repository_User")) {
        FirebaseRepositoryImpl(
            get(),
            get(named("Converter_User")),
            Collection.USER
        )
    }
    single<FirebaseRepository<DriverDto>>(named("Repository_Driver")) {
        FirebaseRepositoryImpl(
            get(),
            get(named("Converter_Driver")),
            Collection.DRIVER
        )
    }
    single<FirebaseRepository<AdminDto>>(named("Repository_Admin")) {
        FirebaseRepositoryImpl(
            get(),
            get(named("Converter_Admin")),
            Collection.ADMIN
        )
    }

    // Converter
    single<FirebaseConverter<BusinessCentralDto>>(named("Converter_BusinessCentral")) {
        FirebaseConverterImpl(
            BusinessCentralDto::class.java
        )
    }
    single<FirebaseConverter<LicensingDto>>(named("Converter_Licensing")) {
        FirebaseConverterImpl(
            LicensingDto::class.java
        )
    }
    single<FirebaseConverter<UserDto>>(named("Converter_User")) { FirebaseConverterImpl(UserDto::class.java) }
    single<FirebaseConverter<DriverDto>>(named("Converter_Driver")) {
        FirebaseConverterImpl(
            DriverDto::class.java
        )
    }
    single<FirebaseConverter<AdminDto>>(named("Converter_Admin")) { FirebaseConverterImpl(AdminDto::class.java) }

}


