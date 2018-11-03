package api
import com.mashape.unirest.http.Unirest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiDocumentationSpec : TestBase() {
    @Test
    fun fetchDocumentation() {
        val r = Unirest
            .get("${apiRoot()}/api_explorer/index.html")
            .asString()
        val contentType = r.headers["Content-Type"]?.get(0) ?: "not-found"
        assertEquals(200, r.status)
        assertEquals("text/html", contentType)
    }
}
