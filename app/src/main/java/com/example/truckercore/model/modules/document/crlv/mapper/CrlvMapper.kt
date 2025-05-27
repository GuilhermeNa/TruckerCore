package com.example.truckercore.model.modules.document.crlv.mapper

import com.example.truckercore._utils.classes.Url
import com.example.truckercore._utils.classes.validity.Validity
import com.example.truckercore._utils.classes.validity.ValidityDto
import com.example.truckercore.model.modules._shared.contracts.mapper.Mapper
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.document.crlv.data.Crlv
import com.example.truckercore.model.modules.document.crlv.data.CrlvDto
import com.example.truckercore.model.modules.document.crlv.data.CrlvID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.shared.utils.expressions.toDate
import com.example.truckercore.model.shared.utils.expressions.toLocalDate
import java.time.Year

object CrlvMapper : Mapper<Crlv, CrlvDto> {

    override fun toDto(entity: Crlv): CrlvDto = try {
        CrlvDto(
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
        )
    } catch (e: Exception) {
        handleError(entity, e)
    }

    override fun toEntity(dto: CrlvDto): Crlv = try {
        Crlv(
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
        )
    } catch (e: Exception) {
        handleError(dto, e)
    }

}