package kady.muhammad.paytabstask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kady.muhammad.ext.MockedServer
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.domain.Result
import kady.muhammad.paytabstask.presentation.screens.characters_screen.CharactersViewModel
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class CharactersViewModelTest {
    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val server: MockedServer = MockedServer()
    private var viewModel: CharactersViewModel? = null
    private var repo: Repo? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        server.start()
        repo = Repo(server.marvelAPI)
        viewModel = CharactersViewModel(repo!!)
    }

    @Test
    fun `charactersList first emits loading`(): Unit = runBlocking {
        server.enqueueSuccess()
        val result = viewModel!!.charactersList(offset = 0).first()
        assert(result == Result.Loading)
    }

    @Test
    fun `charactersList emits success after loading`(): Unit = runBlocking {
        server.enqueueSuccess()
        val result = viewModel!!.charactersList(offset = 0).drop(1).first()
        assert(result is Result.Success<*>)
    }

    @Test
    fun `charactersList emits error`(): Unit = runBlocking {
        //Enqueue Server Error 500
        server.enqueueError(500)
        //Pass invalid offset
        val result = viewModel!!.charactersList(offset = -1).drop(1).first()
        assert(result is Result.Error)
    }

    @After
    fun tearDown() {
        viewModel = null
        repo = null
        server.shutdown()
    }
}