package com.example.truckercore.layers.domain.use_case.user


interface GetUserUserCase {

    suspend operator fun invoke(spec: UserSpec): DataOutcome<User>

}

class GetUserUserCaseImpl(
    private val dataRepository: DataRepository
) : GetUserUserCase {

    override suspend fun invoke(spec: UserSpec): DataOutcome<User> =
        try {

            dataRepository.findOneBy(spec)
                .getOrElse { return it }
                .let { getSuccessResponse(it) }

        } catch (e: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException) {
            DataOutcome.Failure(e)
        } catch (e: Exception) {
            DataOutcome.Failure(
                com.example.truckercore.core.error.classes.technical.TechnicalException.Unknown("$UNEXPECTED_ERROR_MESSAGE $spec", e)
            )
        }

    private fun getSuccessResponse(dto: UserDto): DataOutcome.Success<User> {
        val user = UserMapper.toEntity(dto)
        return DataOutcome.Success(user)
    }

    companion object {
        private const val UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred while fetching the user."
    }

}