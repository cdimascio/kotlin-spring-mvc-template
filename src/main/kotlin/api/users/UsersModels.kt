package api.users

data class UserPutBody(
    val id: Long?,
    val name: String?
)

data class UserPostBody(
    val name: String
)

data class User(
    val id: Long,
    val name: String
)
