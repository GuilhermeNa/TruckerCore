package com.example.truckercore.modules.employee.shared.modules.employee_contract.entities

import com.example.truckercore.modules.employee.shared.modules.employee_contract.enums.EmployeeContractStatus
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime
 //TODO( Preciso tirar o vinculo direto com ferias e etc, isso virá de outro modulo.)
data class EmployeeContract(
    override val masterUid: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val employeeId: String,
    val hireDate: LocalDateTime,
    val status: EmployeeContractStatus,
    val dismissalDate: LocalDateTime? = null,
    //val payrolls: List<Payroll>? = null,
    //val vacations: List<Vacation>? = null,
    //val thirteenths: List<Thirteenth>? = null
): Entity {

}
