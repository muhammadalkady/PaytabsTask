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
    private var _server: MockedServer? = null
    private val server: MockedServer get() = _server!!

    //
    private var _repo: IRepo? = null
    private val repo: IRepo get() = _repo!!

    //
    private var _viewModel: CharactersViewModel? = null
    private val viewModel: CharactersViewModel get() = _viewModel!!

    //
    private val dataMapper = DataCharactersToDomainCharactersMapper()
    private val domainMapper = DomainCharacterToUICharacterMapper()
    private val networkMapper = NetworkCharacterToDBCharacterMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(newFixedThreadPoolContext(1, "Test Thread"))
        MockitoAnnotations.openMocks(this)
        _server = MockedServer()
        server.start()
        _repo = Repo(server.marvelAPI, FakeDB(), dataMapper, networkMapper)
        _viewModel = CharactersViewModel(
            repo = repo,
            domainMapper = domainMapper,
            initCall = false,
            offset = 0
        )
    }

    @Test
    fun `Init state is loading`(): Unit = runTest {
        val result = viewModel.loading.first()
        assert(result)
    }

    @Test
    fun `Characters list should emits EMPTY first`(): Unit = runTest {
        val result = viewModel.result.first()
        assert(result == UICharacterList.EMPTY)
    }

    @Test
    fun `Characters list should emits non empty characters list`(): Unit = runTest {
        server.enqueueSuccess()
        viewModel.charactersList(0)
        val result = viewModel.result.drop(1).first()
        assert(result.items.isNotEmpty())
    }

    @Test
    fun `Error should no be empty when server returns error`(): Unit = runTest {
        server.enqueueError(500)
        viewModel.charactersList(0)
        val result = viewModel.error.drop(1).first()
        assert(result.isNotEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        server.shutdown()
        _viewModel = null
        _repo = null
        _server = null
    }

}