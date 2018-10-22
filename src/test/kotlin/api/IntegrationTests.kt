package api

import api.users.User
import com.mashape.unirest.http.Unirest
import io.github.cdimascio.jwcperrors.ApiError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests : TestBase() {

    @BeforeAll
    fun before() {
    }

    @Test
    fun fetchUser() {
        val r = Unirest.get("${apiRoot()}/users/0").asObject(User::class.java)
        assertEquals(r.body.id, 0)
        assertEquals(r.body.name, "Carmine")
    }

    @Test
    fun fetchUsers() {
        val r = Unirest.get("${apiRoot()}/users").asObject(Array<User>::class.java)
        assertEquals(r.body.size, 2)
        r.body.forEach {
            assertTrue(!it.name.isEmpty())
        }
    }

    @Test
    fun fetchUserNotFound() {
        val r = Unirest.get("${apiRoot()}/users/10").asObject(ApiError::class.java)
        assertNotNull(r.body)
        assertEquals(r.status, 404)
    }
}
