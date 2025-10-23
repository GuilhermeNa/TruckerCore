package com.example.truckercore.layers.data.repository.data

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.data.data_source.data.DataSource
import com.example.truckercore.layers.data.repository.data.expressions.getResponse
import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val dataSource: DataSource
) : DataRepository {

    override suspend fun <D : BaseDto, E : BaseEntity> findOneBy(
        spec: Specification<D>
    ): DataOutcome<E> = try {
        dataSource.findById(spec).getResponse(spec)
    } catch (e: AppException) {
        DataOutcome.Failure(e)
    } catch (e: Exception) {
        DataOutcome.Failure(
            DataException.Unknown(
                message = "An unknown error occurred in Data Repository.", cause = e
            )
        )
    }

    override suspend fun <D : BaseDto, E : BaseEntity> findAllBy(
        spec: Specification<D>
    ): DataOutcome<List<E>> = try {
        dataSource.findAllBy(spec).getResponse(spec)
    } catch (e: AppException) {
        DataOutcome.Failure(e)
    } catch (e: Exception) {
        DataOutcome.Failure(
            DataException.Unknown(
                message = "An unknown error occurred in Data Repository.", cause = e
            )
        )
    }

    override fun <D : BaseDto, E : BaseEntity> flowOneBy(
        spec: Specification<D>
    ): Flow<DataOutcome<E>> =
        dataSource.flowOneBy(spec).map { dto ->
            dto.getResponse<D, E>(spec)
        }.catch { e ->
            if (e is AppException) DataOutcome.Failure(e)
            else DataOutcome.Failure(
                DataException.Unknown(
                    message = "An unknown error occurred in Data Repository.", cause = e
                )
            )
        }

    override fun <D : BaseDto, E : BaseEntity> flowAllBy(
        spec: Specification<D>
    ): Flow<DataOutcome<List<E>>> =
        dataSource.flowAllBy(spec).map<List<D>?, DataOutcome<List<E>>> { dtoList ->
            dtoList.getResponse(spec)
        }.catch { e ->
            if (e is AppException) DataOutcome.Failure(e)
            else DataOutcome.Failure(
                DataException.Unknown(
                    message = "An unknown error occurred in Data Repository.", cause = e
                )
            )

        }

}