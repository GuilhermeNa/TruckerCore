package com.example.truckercore.infrastructure.security.authentication.use_cases

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.infrastructure.security.permissions.configs.DefaultPermissions
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

internal class CreateNewSystemAccessUseCaseImpl(
    private val firebaseRepository: FirebaseRepository,
    private val validatorService: ValidatorService,
    private val centralMapper: BusinessCentralMapper,
    private val userMapper: UserMapper,
    private val driverMapper: DriverMapper,
    private val adminMapper: AdminMapper
) : CreateNewSystemAccessUseCase {

    override fun execute(requirements: NewAccessRequirements): Flow<Response<Unit>> {
        return firebaseRepository.runTransaction { transaction ->
            // Get document references from firebase
            val centralRef = firebaseRepository.createDocument(Collection.CENTRAL)
            val userRef = firebaseRepository.createDocument(Collection.USER)
            val personRef = getPersonRef(requirements)

            // Create the dto objects
            val centralDto = createAndValidateCentral(centralRef = centralRef, userRef = userRef)
            val userDto = createAndValidateUser(
                centralRef = centralRef, userRef = userRef,
                personFlag = requirements.personFlag
            )
            val personDto = createAndValidatePerson(userDto, personRef, requirements)

            // Set the dtos into references
            transaction.set(centralRef, centralDto)
            transaction.set(userRef, userDto)
            transaction.set(personRef, personDto)
        }
    }

    private fun getPersonRef(requirements: NewAccessRequirements): DocumentReference {
        return when (requirements.personFlag) {
            PersonCategory.DRIVER -> firebaseRepository.createDocument(Collection.DRIVER)
            PersonCategory.ADMIN -> firebaseRepository.createDocument(Collection.ADMIN)
        }
    }

    private fun createAndValidatePerson(
        user: UserDto,
        personRef: DocumentReference,
        requirements: NewAccessRequirements
    ): Any {
        return when (requirements.personFlag) {
            PersonCategory.DRIVER -> createDriver(user, personRef, requirements)
            PersonCategory.ADMIN -> createAdmin(user, personRef, requirements)
        }
    }

    private fun createAndValidateCentral(
        centralRef: DocumentReference,
        userRef: DocumentReference
    ): BusinessCentralDto {
        val entity = BusinessCentral(
            businessCentralId = "",
            id = null,
            lastModifierId = userRef.id,
            creationDate = LocalDateTime.now(),
            lastUpdate = LocalDateTime.now(),
            persistenceStatus = PersistenceStatus.PENDING,
            keys = 1
        )
        validatorService.validateForCreation(entity)
        val dto = centralMapper.toDto(entity)
        return dto.initializeId(centralRef.id)
    }

    private fun createAndValidateUser(
        centralRef: DocumentReference,
        userRef: DocumentReference,
        personFlag: PersonCategory
    ): UserDto {
        val entity = User(
            businessCentralId = centralRef.id,
            id = null,
            lastModifierId = userRef.id,
            creationDate = LocalDateTime.now(),
            lastUpdate = LocalDateTime.now(),
            persistenceStatus = PersistenceStatus.PENDING,
            isVip = false,
            vipStart = null,
            vipEnd = null,
            level = Level.MASTER,
            permissions = DefaultPermissions.get(Level.MASTER),
            personFLag = personFlag
        )
        validatorService.validateForCreation(entity)
        val dto = userMapper.toDto(entity)
        return dto.initializeId(userRef.id)
    }

    private fun createAdmin(
        user: UserDto,
        personRef: DocumentReference,
        requirements: NewAccessRequirements
    ): AdminDto {
        val entity = Admin(
            businessCentralId = user.businessCentralId!!,
            id = null,
            lastModifierId = user.id!!,
            creationDate = LocalDateTime.now(),
            lastUpdate = LocalDateTime.now(),
            persistenceStatus = PersistenceStatus.PENDING,
            userId = user.id,
            name = "${requirements.name} ${requirements.surname}",
            email = requirements.email,
            employeeStatus = EmployeeStatus.WORKING
        )
        validatorService.validateForCreation(entity)
        val dto = adminMapper.toDto(entity)
        return dto.initializeId(personRef.id)
    }

    private fun createDriver(
        user: UserDto,
        personRef: DocumentReference,
        requirements: NewAccessRequirements
    ): DriverDto {
        val entity = Driver(
            businessCentralId = user.businessCentralId!!,
            id = null,
            lastModifierId = user.id!!,
            creationDate = LocalDateTime.now(),
            lastUpdate = LocalDateTime.now(),
            persistenceStatus = PersistenceStatus.PENDING,
            userId = user.id,
            name = "${requirements.name} ${requirements.surname}",
            email = requirements.email,
            employeeStatus = EmployeeStatus.WORKING
        )
        validatorService.validateForCreation(entity)
        val dto = driverMapper.toDto(entity)
        return dto.initializeId(personRef.id)
    }

}