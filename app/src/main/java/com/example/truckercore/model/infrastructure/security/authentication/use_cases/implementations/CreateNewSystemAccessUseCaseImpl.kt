package com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.CreateNewSystemAccessUseCase
import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.modules.business_central.factory.BusinessCentralFactory
import com.example.truckercore.model.modules.person.employee.admin.factory.AdminFactory
import com.example.truckercore.model.modules.person.employee.driver.factory.DriverFactory
import com.example.truckercore.model.modules.user.enums.PersonCategory
import com.example.truckercore.model.modules.user.factory.UserFactory
import com.example.truckercore.model.shared.interfaces.Dto
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.flow.Flow

internal class CreateNewSystemAccessUseCaseImpl(
    private val fbRepository: FirebaseRepository,
    private val centralFactory: BusinessCentralFactory,
    private val userFactory: UserFactory,
    private val adminFactory: AdminFactory,
    private val driverFactory: DriverFactory
) : CreateNewSystemAccessUseCase {

    override fun execute(requirements: NewAccessRequirements): Flow<Response<Unit>> {
        return fbRepository.runTransaction { transaction ->
            processCreation(requirements, transaction)
        }
    }

    private fun processCreation(requirements: NewAccessRequirements, transaction: Transaction) {
        // Get document references from firebase
        val docRefCentral = fbRepository.createBlankDocument(Collection.CENTRAL)
        val docRefUser = fbRepository.createBlankDocument(Collection.USER, requirements.uid)
        val docRefPerson = fbRepository.createBlankDocument(
            getPersonCollection(requirements.category)
        )

        // Create the dto objects
        val central = centralFactory.create(
            centralId = docRefCentral.id,
            userId = requirements.uid
        )
        val user = userFactory.create(
            centralId = docRefCentral.id,
            uid = requirements.uid,
            personCategory = requirements.category,
            personLevel = Level.MASTER
        )
        val person = createPerson(
            requirements = requirements,
            centralId = docRefCentral.id,
            personId = docRefPerson.id
        )

        // Set the dtos into references
        transaction.set(docRefCentral, central)
        transaction.set(docRefUser, user)
        transaction.set(docRefPerson, person)
    }

    private fun getPersonCollection(category: PersonCategory): Collection {
        return when (category) {
            PersonCategory.ADMIN -> Collection.ADMIN
            PersonCategory.DRIVER -> Collection.DRIVER
        }
    }

    private fun createPerson(
        requirements: NewAccessRequirements,
        centralId: String,
        personId: String
    ): Dto {
        return when (requirements.category) {
            PersonCategory.DRIVER -> driverFactory.create(
                centralId = centralId,
                userId = requirements.uid,
                personId = personId,
                name = requirements.fullName,
                email = requirements.email
            )

            PersonCategory.ADMIN -> adminFactory.create(
                centralId = centralId,
                userId = requirements.uid,
                personId = personId,
                name = requirements.fullName,
                email = requirements.email
            )
        }
    }

}