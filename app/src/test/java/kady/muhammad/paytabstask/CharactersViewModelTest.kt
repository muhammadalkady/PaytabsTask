package kady.muhammad.paytabstask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kady.muhammad.ext.MockedServer
import kady.muhammad.paytabstask.domain.DataCharactersToDomainCharacters
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacter
import kady.muhammad.paytabstask.presentation.screens.characters_screen.CharactersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.test.*
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
    private val dataCharactersToDomainCharacters = DataCharactersToDomainCharacters()
    private val domainCharacterToUICharacter = DomainCharacterToUICharacter()

    @Before
    fun setUp() {
        Dispatchers.setMain(newFixedThreadPoolContext(1, "Test Thread"))
        MockitoAnnotations.openMocks(this)
        server.start()
        repo = Repo(server.marvelAPI, dataCharactersToDomainCharacters)
        viewModel = CharactersViewModel(
            cc = Dispatchers.Main,
            repo = repo!!,
            domainCharacterToUICharacter = domainCharacterToUICharacter,
            offset = 0
        )
    }

    @Test
    fun `charactersList first emits loading`(): Unit = runTest {
        server.enqueueSuccess()
        val result = viewModel!!.result.first()
        assert(result == Result.Loading)
    }

    @Test
    fun `charactersList emits success after loading`(): Unit = runTest {
        server.enqueueSuccess()
        val result = viewModel!!.result.drop(1).first()
        assert(result is Result.Success<*>)
    }

    @Test
    fun `charactersList emits error`(): Unit = runTest {
        //Enqueue Server Error 500
        server.enqueueError(500)
        //Pass invalid offset
        val result = viewModel!!.result.drop(1).first()
        assert(result is Result.Error)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel = null
        repo = null
        server.shutdown()
    }
}