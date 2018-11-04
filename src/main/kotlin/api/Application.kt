package api

import io.github.cdimascio.dotenv.dotenv
import io.github.cdimascio.japierrors.ApiError
import io.github.cdimascio.japierrors.ApiErrorCreator
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application {
    init {
        ApiError.creator(ApiErrorCreator.BASIC)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

val dotenv = dotenv {
    ignoreIfMissing = true
}
val logger = LoggerFactory.getLogger(dotenv["APP_ID"] ?: "example-service")
