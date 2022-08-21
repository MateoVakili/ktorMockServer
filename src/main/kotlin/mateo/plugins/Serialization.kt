package mateo.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            // extension method of ObjectMapper to allow config etc
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}
