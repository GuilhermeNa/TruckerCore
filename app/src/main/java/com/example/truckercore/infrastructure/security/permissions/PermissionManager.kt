package com.example.truckercore.infrastructure.security.permissions

internal object PermissionManager {



}

enum class Permission {
    CREATE_USER,
    UPDATE_USER,
    ARCHIVE_USER,
    DELETE_USER,
    VIEW_USER,

    CREATE_PERSONAL_DATA,
    UPDATE_PERSONAL_DATA,
    ARCHIVE_PERSONAL_DATA,
    DELETE_PERSONAL_DATA,
    VIEW_PERSONAL_DATA
}

enum class Role {
    ADMIN,
    MODERATOR,
    USER
}

data class User(
    val id: Int,
    val name: String,
    val roles: Set<Role> = setOf(),
    val permissions: Set<Permission> = setOf()
)

class PermissionService {

    // Mapeamento de papéis para permissões
    private val rolePermissions = mapOf(
        Role.ADMIN to setOf(
            Permission.CREATE_USER, Permission.UPDATE_USER, Permission.VIEW_USER,
            Permission.ARCHIVE_USER,Permission.DELETE_USER,
            Permission.CREATE_PERSONAL_DATA, Permission.UPDATE_PERSONAL_DATA,
            Permission.ARCHIVE_PERSONAL_DATA, Permission.DELETE_PERSONAL_DATA,
            Permission.VIEW_PERSONAL_DATA
        ),
        Role.MODERATOR to setOf(
            Permission.VIEW_USER
        ),
        Role.USER to setOf(

        )
    )

    // Método para obter permissões de um usuário, combinando papéis e permissões diretas
    fun getPermissions(user: User): Set<Permission> {
        val rolePermissions = user.roles.flatMap { rolePermissions[it] ?: emptySet() }
        return (rolePermissions + user.permissions).toSet()
    }

    // Verifica se o usuário tem uma permissão específica
    fun hasPermission(user: User, permission: Permission): Boolean {
        return getPermissions(user).contains(permission)
    }

    // Adicionar permissão direta ao usuário
    fun grantPermission(user: User, permission: Permission): User {
        return user.copy(permissions = user.permissions + permission)
    }

    // Revogar permissão direta do usuário
    fun revokePermission(user: User, permission: Permission): User {
        return user.copy(permissions = user.permissions - permission)
    }

}

class PermissionChecker(private val permissionService: PermissionService) {

    // Verifica se o usuário tem permissão para realizar uma ação
    fun canPerformAction(user: User, permission: Permission): Boolean {
        return permissionService.hasPermission(user, permission)
    }

}

fun main() {

    val permissionService = PermissionService()
    val permissionChecker = PermissionChecker(permissionService)

    // Criando usuários
    val admin = User(id = 1, name = "Admin", roles = setOf(Role.ADMIN))
    val moderator = User(id = 2, name = "Moderator", roles = setOf(Role.MODERATOR))
    val user = User(id = 3, name = "User", roles = setOf(Role.USER))

    // Verificando permissões
    println("Admin can CREATE_USER: ${permissionChecker.canPerformAction(admin, Permission.CREATE_USER)}")  // true
    println("Moderator can DELETE_USER: ${permissionChecker.canPerformAction(moderator, Permission.DELETE_USER)}")  // false
    // Adicionando uma permissão direta a um usuário
    val userWithExtraPermission = permissionService.grantPermission(user, Permission.CREATE_USER)
    println("User with extra permission can CREATE_USER: ${permissionChecker.canPerformAction(userWithExtraPermission, Permission.CREATE_USER)}")  // true

}