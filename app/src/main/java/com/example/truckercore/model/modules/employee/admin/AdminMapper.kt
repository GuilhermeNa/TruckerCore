package com.example.truckercore.model.modules.employee.admin

import com.example.truckercore.model.modules._contracts.Mapper
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminDto

object AdminMapper: Mapper<Admin, AdminDto> {

    override fun toDto(entity: Admin): AdminDto {
        TODO("Not yet implemented")
    }

    override fun toEntity(dto: AdminDto): Admin {
        TODO("Not yet implemented")
    }

}