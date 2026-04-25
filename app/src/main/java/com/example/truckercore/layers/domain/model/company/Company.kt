package com.example.truckercore.layers.domain.model.company

import com.example.truckercore.layers.domain.base.contracts.BaseEntity
import com.example.truckercore.layers.domain.base.contracts.Optional
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.others.Cnpj
import com.example.truckercore.layers.domain.base.others.CompanyName
import com.example.truckercore.layers.domain.base.others.MunicipalRegistration
import com.example.truckercore.layers.domain.base.others.StateRegistration
import com.example.truckercore.layers.domain.departments.fleet.FleetDepartment
import com.example.truckercore.layers.domain.departments.hr.HrDepartment
import com.example.truckercore.layers.domain.model.antt.AnttCollection
import com.example.truckercore.layers.domain.model.social_contract.SocialContractCollection
import java.time.LocalDate

/**
 * Represents a company within the system.
 *
 * A Company is a root entity that aggregates internal departments
 * such as HR and Fleet.
 */
class Company(
    override val id: CompanyID,
    override val status: Status,
    private var _cnpj: Cnpj? = null,
    private var _name: CompanyName? = null,
    private var _stateRegistration: StateRegistration? = null,
    private var _municipalRegistration: MunicipalRegistration? = null,
    private var _opening: LocalDate? = null
) : BaseEntity, Optional<CompanyOptional, Company> {

    // Getters
    val cnpj get() = _cnpj

    val name get() = _name

    val stateRegistration get() = _stateRegistration

    val municipalRegistration get() = _municipalRegistration

    val opening get() = _opening

    // Collections
    private val anttCollection = AnttCollection()

    private val socialContractCollection = SocialContractCollection()

    // Departments
    private val hr = HrDepartment()

    private val fleet = FleetDepartment()

    //----------------------------------------------------------------------------------------------
    // Methods
    //----------------------------------------------------------------------------------------------
    override fun isFilled(): Boolean =
        cnpj != null ||
                name != null ||
                stateRegistration != null ||
                municipalRegistration != null ||
                opening != null

    override fun completeRegistration(data: CompanyOptional): Company {
        return Company(
            id = this.id,
            status = this.status,
            _cnpj = data.cnpj ?: cnpj,
            _name = data.name ?: name,
            _stateRegistration = data.stateRegistration ?: stateRegistration,
            _municipalRegistration = data.municipalRegistration ?: municipalRegistration,
            _opening = data.opening ?: opening
        )
    }

}

