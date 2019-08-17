import co.devhack.accounts.app.account
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AccountTest {

    @Test
    fun accountCreatTest() = withTestApplication(
        {
            account()
        }
    ) {

        with(handleRequest(HttpMethod.Post, "/account/") {
            addHeader("Content-Type", "application/json")
            setBody(
                "{\n" +
                        "\t\"description\" : \"Juan G\",\n" +
                        "\t\"dateCreated\" : \"01/01/2019\",\n" +
                        "\t\"money\" : 250000\n" +
                        "}"
            )
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
            assertTrue { response.content != null }
            assertTrue { response.content!!.isNotEmpty() }
        }

        with(handleRequest(HttpMethod.Get, "/account/")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertTrue { response.content != null && response.content!!.isNotEmpty() }
        }

    }
}