package com.example.truckercore.infrastructure.security.authentication.use_cases

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.infrastructure.security.permissions.configs.DefaultPermissions
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.person.employee.shared.enums.EmployeeStatus
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
            val (centralRef, userRef, personRef) = createDocumentReferences(requirements)

            // Create the dto objects
            val (centralDto, userDto, personDto) = createDtosToBeSaved(
                centralRef, userRef, requirements, personRef
            )

            // Set the dtos into references
            transaction.set(centralRef, centralDto)
            transaction.set(userRef, userDto)
            transaction.set(personRef, personDto)
        }
    }

    /**
     * Creates references for central, user, and person entities in Firebase based on the requirements.
     *
     * @param requirements The [NewAccessRequirements] containing the data to create the references.
     * @return A [Triple] containing the references for the central, user, and person entities.
     */
    private fun createDocumentReferences(
        requirements: NewAccessRequirements
    ): Triple<DocumentReference, DocumentReference, DocumentReference> {
        val centralRef = firebaseRepository.createDocument(Collection.CENTRAL)
        val userRef = firebaseRepository.createDocument(Collection.USER)
        val personRef = when (requirements.personFlag) {
            PersonCategory.DRIVER -> firebaseRepository.createDocument(Collection.DRIVER)
            PersonCategory.ADMIN -> firebaseRepository.createDocument(Collection.ADMIN)
        }
        return Triple(centralRef, userRef, personRef)
    }

    /**
     * Creates the necessary DTOs to be saved in Firebase.
     *
     * @param centralRef The reference for the central entity in Firebase.
     * @param userRef The reference for the user entity in Firebase.
     * @param requirements The [NewAccessRequirements] containing the data for the new system access.
     * @param personRef The reference for the person entity in Firebase (either driver or admin).
     * @return A [Triple] containing the DTOs for central, user, and person entities.
     */
    private fun createDtosToBeSaved(
        centralRef: DocumentReference,
        userRef: DocumentReference,
        requirements: NewAccessRequirements,
        personRef: DocumentReference
    ): Triple<BusinessCentralDto, UserDto, Any> {
        val centralDto = createAndValidateCentral(centralRef = centralRef, userRef = userRef)
        val userDto = createAndValidateUser(
            centralRef = centralRef, userRef = userRef,
            personFlag = requirements.personFlag
        )
        val personDto = when (requirements.personFlag) {
            PersonCategory.DRIVER -> createDriver(userDto, personRef, requirements)
            PersonCategory.ADMIN -> createAdmin(userDto, personRef, requirements)
        }
        return Triple(centralDto, userDto, personDto)
    }

    /**
     * Creates and validates a central entity and returns its corresponding DTO.
     *
     * @param centralRef The reference for the central entity in Firebase.
     * @param userRef The reference for the user entity in Firebase.
     * @return The DTO for the central entity.
     */
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

    /**
     * Creates and validates a user entity and returns its corresponding DTO.
     *
     * @param centralRef The reference for the central entity in Firebase.
     * @param userRef The reference for the user entity in Firebase.
     * @param personFlag The flag indicating the type of person (Driver or Admin).
     * @return The DTO for the user entity.
     */
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

    /**
     * Creates an admin entity and returns its corresponding DTO.
     *
     * @param user The user DTO to associate with the admin.
     * @param personRef The reference for the admin entity in Firebase.
     * @param requirements The [NewAccessRequirements] containing the admin details.
     * @return The DTO for the admin entity.
     */
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

    /**
     * Creates a driver entity and returns its corresponding DTO.
     *
     * @param user The user DTO to associate with the driver.
     * @param personRef The reference for the driver entity in Firebase.
     * @param requirements The [NewAccessRequirements] containing the driver details.
     * @return The DTO for the driver entity.
     */
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