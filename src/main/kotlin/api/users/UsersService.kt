package api.users

typealias UserUpdates = UserPutBody
typealias UserDetails = UserPostBody

class UsersService {
    private val users = sortedMapOf(
        0L to User(id = 0, name = "Carmine"),
        1L to User(id = 1, name = "Laura")
    )

    fun all() = users.values.toList()
    fun byId(id: Long) = users[id]
    fun delete(id: Long) = users.remove(id)

    fun create(user: UserDetails): User {
        val id = users.size.toLong()
        val newUser = User(
            id = id,
            name = user.name
        )
        users[id] = newUser
        return newUser
    }

    fun update(user: UserUpdates): User? {
        if (user.id == null) return null
        return byId(user.id)?.let {
            val updatedUser = User(
                id = user.id,
                name = user.name ?: it.name
            )
            users[user.id] = updatedUser
            updatedUser
        }
    }
}
