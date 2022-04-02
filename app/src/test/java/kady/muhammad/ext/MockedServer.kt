package kady.muhammad.ext

import kady.muhammad.paytabstask.data.network.MarvelAPI
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MockedServer {
    private val server: MockWebServer = MockWebServer()
    val marvelAPI by lazy { MarvelAPI(server.url("/").toString()) }

    fun start() {
        server.start(MOCK_WEBSERVER_PORT)
    }

    fun shutdown() {
        server.shutdown()
    }

    fun enqueueSuccess() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    MockResponseFileReader("characters_list_json_success.json")
                        .content
                )
        )
    }

    fun enqueueError(code: Int) {
        server.enqueue(
            MockResponse().setHttp2ErrorCode(code)
        )
    }

    companion object {
        private const val MOCK_WEBSERVER_PORT = 8000

    }
}