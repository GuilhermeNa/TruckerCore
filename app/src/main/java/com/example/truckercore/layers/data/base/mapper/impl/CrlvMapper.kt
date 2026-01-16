package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.layers.data.base.dto.impl.CrlvDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.model.crlv.Crlv

object CrlvMapper : Mapper<CrlvDto, Crlv> {

    override fun toDto(entity: Crlv): CrlvDto = try {
        TODO()
    /*    CrlvDto(
            id = entity.id.value,
            companyId = entity.companyId.value,
            persistenceState = entity.persistenceState,
            validityDto = ValidityDto(
                fromDate = entity.validity.fromDate.toDate(),
                toDate = entity.validity.toDate.toDate()
            ),
            refYear = entity.refYear.value,
            plate = entity.plate.value,
            url = entity.url.value
        )*/
    } catch (e: Exception) {
        TODO()
       // handleError(entity, e)
    }

    override fun toEntity(dto: CrlvDto): Crlv = try {
        TODO()
      /*  Crlv(
            id = CrlvID(dto.id!!),
            companyId = CompanyID(dto.companyId!!),
            persistenceState = dto.persistenceState!!,
            validity = Validity(
                fromDate = dto.validityDto!!.fromDate!!.toLocalDate(),
                toDate = dto.validityDto.toDate!!.toLocalDate()
            ),
            refYear = Year.of(dto.refYear!!),
            plate = Plate(dto.plate!!),
            url = Url(dto.url!!)
        )*/
    } catch (e: Exception) {
        TODO()
       // handleError(dto, e)
    }

    override fun toDtos(entities: List<Crlv>): List<CrlvDto> {
        TODO("Not yet implemented")
    }

    override fun toEntities(dtos: List<CrlvDto>): List<Crlv> {
        TODO("Not yet implemented")
    }

}