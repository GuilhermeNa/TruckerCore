package com.example.truckercore.business_admin.layers.domain.use_case.company

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque
import com.example.truckercore.layers.data.base.instruction.instructions.Put
import com.example.truckercore.layers.data.base.mapper.impl.AccessMapper
import com.example.truckercore.layers.data.base.mapper.impl.AdminMapper
import com.example.truckercore.layers.data.base.mapper.impl.CompanyMapper
import com.example.truckercore.layers.data.base.mapper.impl.UserMapper
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.repository.instruction.InstructionRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.AccessFactory
import com.example.truckercore.layers.domain.model.admin.AdminFactory
import com.example.truckercore.layers.domain.model.company.CompanyFactory
import com.example.truckercore.layers.domain.model.user.UserFactory

class InitializeCompanyAccessUseCase(
    val instructionRepository: InstructionRepository,
) {

    suspend operator fun invoke(uid: UID, name: Name, email: Email): OperationOutcome {
        val deque = InstructionDeque()

        // Create Domain Objects
        val userId = UserID.generate()

        val company = CompanyFactory()
        val access = AccessFactory.toAdmin(userId, company.id)
        val draftUser = UserFactory.toDraft(uid, userId, company.id)
        val admin = AdminFactory(company.id, name, email, userId)

        // Map to Dto
        val companyDto = CompanyMapper.toDto(company)
        val accessDto = AccessMapper.toDto(access)
        val userDto = UserMapper.toDto(draftUser)
        val adminDto = AdminMapper.toDto(admin)

        // Create and add instructions to the deque
        deque.addInstruction(
            Put(companyDto),
            Put(accessDto),
            Put(userDto),
            Put(adminDto)
        )

        return instructionRepository(deque)
    }

}