package api

import com.fasterxml.jackson.core.JsonProcessingException
import com.mashape.unirest.http.ObjectMapper
import com.mashape.unirest.http.Unirest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import java.io.IOException

@SpringBootTest
class TestBase {
    init {
        Unirest.setObjectMapper(object : ObjectMapper {
            private val jacksonObjectMapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
            override fun <T> readValue(value: String, valueType: Class<T>): T {
                try {
                    return jacksonObjectMapper.readValue(value, valueType)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }

            override fun writeValue(value: Any): String {
                try {
                    return jacksonObjectMapper.writeValueAsString(value)
                } catch (e: JsonProcessingException) {
                    throw RuntimeException(e)
                }
            }
        })
    }

    @Autowired
    lateinit var environment: Environment

    fun apiRoot() = "http://localhost:${environment.getProperty("local.server.port")!!}"

}
