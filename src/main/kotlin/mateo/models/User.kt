package mateo.models

data class User(
    val id: Int,
    val fullName: String,
    val avatar: String,
    val email: String,
    val authToken: String? = null,
    val created: String
)