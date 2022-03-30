package kady.muhammad.paytabstask

import kady.muhammad.ext.MockedServer
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class MarvelAPIUnitTest {

    private val server: MockedServer = MockedServer()

    @Before
    fun init() {
        server.start()
    }

    @Test
    fun `MarvelAPI parses json successfully`() = runBlocking {
        server.enqueueSuccess()
        val result = server.marvelAPI.getCharacterList(offset = 0)
        assert(result.data.character.isNotEmpty())
    }

    @After
    fun shutdown() {
        server.shutdown()
    }
}