package com.example.truckercore.model.modules.company.mapper

import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.modules._contracts.mapper.Mapper
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.data.CompanyID

object CompanyMapper : Mapper<Company, CompanyDto> {

    override fun toDto(entity: Company): CompanyDto =
        try {
            CompanyDto(
                id = entity.idVal,
                persistence = entity.persistence,
                keysRegistry = entity.keysValue
            )
        } catch (e: Exception) {
            handleError(entity, e)
        }

    override fun toEntity(dto: CompanyDto): Company =
        try {
            Company(
                id = CompanyID(dto.id!!),
                persistence = dto.persistence!!,
                keysRegistry = ValidKeysRegistry.from(dto.keysRegistry!!)
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

}