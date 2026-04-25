package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data.EditBusinessView
import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.others.Cnpj
import com.example.truckercore.layers.domain.base.others.CompanyName
import com.example.truckercore.layers.domain.base.others.MunicipalRegistration
import com.example.truckercore.layers.domain.base.others.StateRegistration
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.model.company.CompanyOptional
import java.time.LocalDate

object CompanyMapper : Mapper<CompanyDto, Company> {

    override fun toDto(entity: Company): CompanyDto = try {
        with(entity) {
            CompanyDto(id = idValue, status = status)
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping a Company to dto: $entity")
    }

    override fun toEntity(dto: CompanyDto): Company = try {
        with(dto) {
            Company(id = CompanyID(id!!), status = status!!)
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an CompanyDto to entity: $dto")
    }

    override fun toDtos(entities: List<Company>): List<CompanyDto> = entities.map { toDto(it) }

    override fun toEntities(dtos: List<CompanyDto>): List<Company> = dtos.map { toEntity(it) }

}