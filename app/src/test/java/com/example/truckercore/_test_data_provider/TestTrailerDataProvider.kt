package com.example.truckercore._test_data_provider

import com.example.truckercore.model.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.model.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.model.shared.enums.PersistenceStatus
import java.time.LocalDateTime
import java.util.Date

object TestTrailerDataProvider {

    fun getBaseEntity() = Trailer(
        businessCentralId = "123",
        id = "id",
        lastModifierId = "modifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        plate = "PLATE123",
        color = "Red",
        brand = TrailerBrand.RANDOM,
        category = TrailerCategory.FOUR_AXIS,
        truckId = "truckId"
    )

    fun getBaseDto() = TrailerDto(
        businessCentralId = "123",
        id = "1",
        lastModifierId = "admin",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        plate = "PLATE123",
        color = "Red",
        brand = TrailerBrand.RANDOM.name,
        category = TrailerCategory.FOUR_AXIS.name,
        truckId = "truckId"
    )

    fun arrInvalidDtos() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(plate = null),
        getBaseDto().copy(color = null),
        getBaseDto().copy(brand = null),
        getBaseDto().copy(category = null)
    )

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = "ARCHIVED")
    )

    fun arrInvalidDtosForValidationRules() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(businessCentralId = ""),
        getBaseDto().copy(businessCentralId = " "),
        getBaseDto().copy(id = null),
        getBaseDto().copy(id = ""),
        getBaseDto().copy(id = " "),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(lastModifierId = ""),
        getBaseDto().copy(lastModifierId = " "),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(persistenceStatus = ""),
        getBaseDto().copy(persistenceStatus = " "),
        getBaseDto().copy(persistenceStatus = "PENDING"),
        getBaseDto().copy(persistenceStatus = "INVALID"),
        getBaseDto().copy(plate = null),
        getBaseDto().copy(plate = ""),
        getBaseDto().copy(plate = " "),
        getBaseDto().copy(color = null),
        getBaseDto().copy(color = ""),
        getBaseDto().copy(color = " "),
        getBaseDto().copy(brand = null),
        getBaseDto().copy(brand = ""),
        getBaseDto().copy(brand = " "),
        getBaseDto().copy(brand = "INVALID"),
        getBaseDto().copy(category = null),
        getBaseDto().copy(category = ""),
        getBaseDto().copy(category = " "),
        getBaseDto().copy(category = "INVALID")
    )

    fun arrValidEntitiesForValidationRules() = arrayOf(
        getBaseEntity(),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED)
    )

    fun arrInvalidEntitiesForValidationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = ""),
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(id = null),
        getBaseEntity().copy(id = ""),
        getBaseEntity().copy(id = " "),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PENDING),
        getBaseEntity().copy(plate = ""),
        getBaseEntity().copy(plate = " "),
        getBaseEntity().copy(color = ""),
        getBaseEntity().copy(color = " ")
    )

    fun arrValidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = ""),
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(id = "123"),
        getBaseEntity().copy(),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PERSISTED),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED),
        getBaseEntity().copy(plate = ""),
        getBaseEntity().copy(plate = " "),
        getBaseEntity().copy(color = ""),
        getBaseEntity().copy(color = " ")
    )

}