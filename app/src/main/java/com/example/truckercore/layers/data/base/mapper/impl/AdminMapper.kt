package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.data.base.dto.impl.AdminDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.base.ids.AdminID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.admin.Admin

object AdminMapper : Mapper<AdminDto, Admin> {

    override fun toDto(entity: Admin): AdminDto = try {
        with(entity) {
            AdminDto(
                id = idValue,
                companyId = idValue,
                status = status,
                name = nameValue(),
                email = emailValue(),
                userId = emailValue()
            )
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an Admin to dto: $entity")
    }

    override fun toEntity(dto: AdminDto): Admin = try {
        with(dto) {
            Admin(
                id = AdminID(id!!),
                companyId = CompanyID(companyId!!),
                status = status!!,
                name = Name.from(name!!),
                email = Email.from(email!!),
                userId = userId?.let { UserID(it) }
            )
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an AdminDto to entity: $dto")
    }

    override fun toDtos(entities: List<Admin>): List<AdminDto> =
        entities.map { toDto(it) }

    override fun toEntities(dtos: List<AdminDto>): List<Admin> =
        dtos.map { toEntity(it) }

}