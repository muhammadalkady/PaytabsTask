package kady.muhammad.paytabstask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kady.muhammad.ext.FakeDB
import kady.muhammad.ext.MockedServer
import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacterMapper
import kady.muhammad.paytabstask.domain.DataCharactersToDomainCharactersMapper
import kady.muhammad.paytabstask.domain.IRepo
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacterMapper
import kady.muhammad.paytabstask.presentation.entities.UICharacterList
import kady.muhammad.paytabstask.presentation.screens.characters_screen.CharactersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class CharactersViewModelTest {
    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    //
    private val dataMapper = DataCharactersToDomainCharactersMapper()
    private val domainMapper = DomainCharacterToUICharacterMapper()
    private val networkMapper = NetworkCharacterToDBCharacterMapper()

    //
    private val server: MockedServer = MockedServer().apply { start() }
    private val repo: IRepo = Repo(server.marvelAPI, FakeDB(), dataMapper, networkMapper)
    private var viewModel: CharactersViewModel = CharactersViewModel(
        repo = repo,
        domainMapper = domainMapper,
        initCall = false,
        page = 0
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(newFixedThreadPoolContext(1, "Test Thread"))
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Initial state should be loading`(): Unit = runTest {
        val result = viewModel.loading.first()
        assert(result)
    }

    @Test
    fun `Initial UICharactersList should be EMPTY`(): Unit = runTest {
        val result = viewModel.result.first()
        assert(result == UICharacterList.EMPTY)
    }

    @Test
    fun `UICharactersList should not be empty after a success call to API`(): Unit =
        runTest {
            server.enqueueSuccess()
            viewModel.charactersList(1, fromCacheFirst = false)
            val result = viewModel.result.drop(1).first()
            assert(result.items.isNotEmpty())
        }

    @Test
    fun `Error should no be empty after a server error`(): Unit = runTest {
        server.enqueueError(500)
        viewModel.charactersList(1, fromCacheFirst = false)
        val result = viewModel.error.drop(1).first()
        assert(result.isNotEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        server.shutdown()
    }

}