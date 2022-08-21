package mateo.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import mateo.BaseResponse
import mateo.models.CreateUserParams
import mateo.repository.UserRepository

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

fun Application.authRouting(userRepository: UserRepository) {
    routing {
        route("/auth") {
            post("/register") {
                try {
                    val param = call.receive<CreateUserParams>()
                    val result = userRepository.register(param)
                    call.respond(result)
                } catch (e: BadRequestException) {
                    call.respond(
                        HttpStatusCode.BadRequest, BaseResponse.Error(
                            message = "invalid request"
                        )
                    )
                }
            }
        }
    }
}
