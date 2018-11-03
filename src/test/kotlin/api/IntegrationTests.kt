package api

import api.users.User
import api.users.UserPostBody
import api.users.UserPutBody
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
        Unirest.setDefaultHeader("content-type", "application/json")
    }

    @Test
    fun fetchUser() {
        val r = Unirest
            .get("${apiRoot()}/users/0")
            .asObject(User::class.java)

        assertEquals(0, r.body.id)
        assertEquals("Carmine", r.body.name)
    }

    @Test
    fun fetchUsers() {
        val r = Unirest
            .get("${apiRoot()}/users")
            .asObject(Array<User>::class.java)

        assertEquals(2, r.body.size)
        r.body.forEach {
            assertTrue(!it.name.isEmpty())
        }
    }

    @Test
    fun createUser() {
        val name = "Test User"
        val r = Unirest
            .post("${apiRoot()}/users")
            .body(UserPostBody(name))
            .asObject(User::class.java)

        assertEquals(2, r.body.id)
        assertEquals(name, r.body.name)
    }

    @Test
    fun updateUser() {
        val updatedUser = User(id = 0, name = "Test Updated User")
        val r = Unirest
            .put("${apiRoot()}/users/${updatedUser.id}")
            .body(UserPutBody(
                id = updatedUser.id,
                name = updatedUser.name
            ))
            .asObject(User::class.java)

        assertEquals(updatedUser.id, r.body.id)
        assertEquals(updatedUser.name, r.body.name)
    }

    @Test
    fun deleteUser() {
        val id = 0L
        val r = Unirest
            .delete("${apiRoot()}/users/$id")
            .asObject(User::class.java)

        assertEquals(id, r.body.id)
    }

    @Test
    fun fetchUserNotFound() {
        val r = Unirest
            .get("${apiRoot()}/users/10")
            .asObject(ApiError::class.java)

        assertNotNull("not_found", r.body.errors[0].code)
        assertEquals(404, r.status)
    }
}
