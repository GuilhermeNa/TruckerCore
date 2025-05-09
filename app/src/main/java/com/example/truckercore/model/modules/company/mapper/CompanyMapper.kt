package com.example.truckercore.model.modules.company.mapper

import com.example.truckercore.model.modules._contracts.Mapper
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyDto

object CompanyMapper: Mapper<Company, CompanyDto> {

    override fun toDto(entity: Company): CompanyDto {
        TODO("Not yet implemented")
    }

    override fun toEntity(dto: CompanyDto): Company {
        TODO("Not yet implemented")
    }

}