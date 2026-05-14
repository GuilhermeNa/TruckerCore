package com.example.truckercore.layers.data_2.repository.interfaces

import com.example.truckercore.core.my_lib.classes.Cpf
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.driver.Driver
import kotlinx.coroutines.flow.Flow

interface DriverRepository {

    fun observe(userId: UserID): Flow<DataOutcome<Driver>>

    fun observeList(companyId: CompanyID): Flow<DataOutcome<List<Driver>>>

    fun fetch(cpf: Cpf): DataOutcome<Driver>
}