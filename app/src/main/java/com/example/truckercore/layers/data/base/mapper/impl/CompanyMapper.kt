package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.model.company.Company

object CompanyMapper : Mapper<CompanyDto, Company> {

    override fun toDto(entity: Company): CompanyDto = try {
        TODO()
        /*      CompanyDto(
                  id = entity.idVal,
                  persistenceState = entity.status,
                  keysRegistry = entity.keysValue.toList()
              )*/
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an entity to dto: $entity")
    }

    override fun toEntity(dto: CompanyDto): Company = try {
        TODO()
        /*    Company(
                id = CompanyID(dto.id!!),
                status = dto.persistenceState!!,
                keysCollection = ValidKeysRegistry.from(dto.keysRegistry!!)
            )*/
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an dto to entity: $dto")
    }

    override fun toDtos(entities: List<Company>): List<CompanyDto> = entities.map { toDto(it) }

    override fun toEntities(dtos: List<CompanyDto>): List<Company> = dtos.map { toEntity(it) }

}