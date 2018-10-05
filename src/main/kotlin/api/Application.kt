package api

import io.github.cdimascio.dotenv.dotenv
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

val dotenv = dotenv {
    ignoreIfMissing = true
}
val logger = LoggerFactory.getLogger(dotenv["APP_ID"] ?: "example-service")
