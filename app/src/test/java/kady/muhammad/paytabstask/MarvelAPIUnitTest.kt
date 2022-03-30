package kady.muhammad.paytabstask

import kady.muhammad.paytabstask.data.network.MarvelAPI
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val MOCK_WEBSERVER_PORT = 8000

class MarvelAPIUnitTest {

    private val server: MockWebServer = MockWebServer()
    private val marvelAPI by lazy { MarvelAPI(server.url("/").toString()) }

    @Before
    fun init() {
        server.start(MOCK_WEBSERVER_PORT)
    }

    @Test
    fun `MarvelAPI parses json successfully`() = runBlocking {
        server.enqueue(
            MockResponse()
                .setBody(
                    MockResponseFileReader("characters_list_json_success.json")
                        .content
                )
        )
        val result = marvelAPI.getCharacterList(offset = 0)
        assert(result.data.character.isNotEmpty())
    }

    @After
    fun shutdown() {
        server.shutdown()
    }
}