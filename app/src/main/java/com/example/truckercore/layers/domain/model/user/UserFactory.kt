package com.example.truckercore.layers.domain.model.user

object UserFactory {

    operator fun invoke(form: UserForm): User {
        TODO()
        /*return User(
            id = UserID.generate(),
            companyId = form.companyId,
            uid = form.uid,
            profile = getProfile(form.role),
            persistenceState = PersistenceState.ACTIVE
        )*/
    }

/*    private fun getProfile(role: Role) = Profile(
        role = role,
        permissions = DefaultPermissionsProvider(role)
    )*/

}