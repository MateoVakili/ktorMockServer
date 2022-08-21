package mateo.service

import mateo.db.DatabaseFactory.dbQuery
import mateo.db.UserTable
import mateo.models.CreateUserParams
import mateo.models.User
import mateo.security.hash
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

interface UserService {
    suspend fun registerUser(params: CreateUserParams): User?
    suspend fun findUserByEmail(email: String): User?
}

class UserServiceImpl : UserService {
    override suspend fun registerUser(params: CreateUserParams): User? {
        return dbQuery {
            UserTable.insert {
                it[email] = params.email
                it[password] = hash(params.password) // encrypt
                it[avatar] = params.avatar
                it[fullName] = params.fullName
            }
        }.resultedValues
            ?.map { rowToUser(it) }
            ?.singleOrNull()
    }

    override suspend fun findUserByEmail(email: String): User? {
        return rowToUser(dbQuery { UserTable.select { UserTable.email.eq(email) }.firstOrNull() })
    }

    private fun rowToUser(row: ResultRow?): User? {
        return row?.let {
            User(
                id = row[UserTable.id],
                fullName = row[UserTable.fullName],
                avatar = row[UserTable.avatar],
                email = row[UserTable.email],
                created = row[UserTable.created].toString()
            )
        }
    }
}