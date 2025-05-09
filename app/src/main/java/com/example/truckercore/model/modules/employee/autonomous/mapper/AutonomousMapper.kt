package com.example.truckercore.model.modules.employee.autonomous.mapper

import com.example.truckercore.model.modules._contracts.Mapper
import com.example.truckercore.model.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto

object AutonomousMapper: Mapper<Autonomous, AutonomousDto> {

    override fun toDto(entity: Autonomous): AutonomousDto {
        TODO("Not yet implemented")
    }

    override fun toEntity(dto: AutonomousDto): Autonomous {
        TODO("Not yet implemented")
    }

}