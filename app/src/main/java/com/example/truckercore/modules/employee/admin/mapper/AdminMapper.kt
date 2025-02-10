package com.example.truckercore.modules.employee.admin.mapper

import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.errors.AdminMappingException
import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

/*
internal class AdminMapper : Mapper<Admin, AdminDto>() {

    override fun handleEntityMapping(entity: Admin) = AdminDto(
        businessCentralId = entity.businessCentralId,
        id = entity.id,
        lastModifierId = entity.lastModifierId,
        creationDate = entity.creationDate.toDate(),
        lastUpdate = entity.lastUpdate.toDate(),
        persistenceStatus = entity.persistenceStatus.name,
        userId = entity.userId,
        name = entity.name,
        email = entity.email,
        employeeStatus = entity.employeeStatus.name
    )

    override fun handleDtoMapping(dto: AdminDto) = Admin(
        businessCentralId = dto.businessCentralId!!,
        id = dto.id!!,
        lastModifierId = dto.lastModifierId!!,
        creationDate = dto.creationDate!!.toLocalDateTime(),
        lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
        persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
        userId = dto.userId,
        name = dto.name!!,
        email = dto.email!!,
        employeeStatus = EmployeeStatus.convertString(dto.employeeStatus)
    )

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logError(message)
        throw AdminMappingException(message = "$message Obj: $obj", receivedException)
    }

}*/
