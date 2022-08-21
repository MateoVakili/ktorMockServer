package mateo.repository

import mateo.BaseResponse
import mateo.models.CreateUserParams
import mateo.models.User
import mateo.service.UserService

interface UserRepository {
    suspend fun register(param: CreateUserParams): BaseResponse<Any>
    suspend fun login(email: String, password: String): BaseResponse<Any>
}

class UserRepositoryImpl(
    private val userService: UserService
): UserRepository {
    override suspend fun register(param: CreateUserParams): BaseResponse<User> {
        return userService.findUserByEmail(param.email)?.let {
            BaseResponse.Error(message = "Email already used")
        } ?: userService.registerUser(param)?.let {
            // do token related stuff
            BaseResponse.Success(data = it)
        } ?: BaseResponse.Error(message = "could not register user")
    }

    override suspend fun login(email: String, password: String): BaseResponse<Any> {
        // nothing for now
        return BaseResponse.Error(message = "cant login yet!!")
    }
}