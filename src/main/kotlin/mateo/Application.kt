package mateo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import mateo.db.DatabaseFactory
import mateo.plugins.*
import mateo.repository.UserRepositoryImpl
import mateo.service.UserServiceImpl

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        DatabaseFactory.init()
    //   configureSecurity()
        configureSerialization()
        configureRouting()

        // figure out how to do DI
        authRouting(UserRepositoryImpl(UserServiceImpl()))
    }.start(wait = true)
}
