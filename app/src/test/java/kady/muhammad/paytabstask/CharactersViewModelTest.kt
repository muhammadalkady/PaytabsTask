package kady.muhammad.paytabstask

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kady.muhammad.ext.MockedServer
import kady.muhammad.paytabstask.domain.DataCharactersToDomainCharacters
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacter
import kady.muhammad.paytabstask.data.db.DBCharacter
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacter
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
    private var server: MockedServer? = null
    private var viewModel: CharactersViewModel? = null
    private var repo: Repo? = null
    private val dataCharactersToDomainCharacters = DataCharactersToDomainCharacters()
    private val domainCharacterToUICharacter = DomainCharacterToUICharacter()
    private val networkCharacterToDBCharacter = NetworkCharacterToDBCharacter()

    @Before
    fun setUp() {
        Dispatchers.setMain(newFixedThreadPoolContext(1, "Test Thread"))
        MockitoAnnotations.openMocks(this)
        server = MockedServer()
        server!!.start()
        repo = Repo(
            server!!.marvelAPI,
            FakeDB(),
            dataCharactersToDomainCharacters,
            networkCharacterToDBCharacter
        )
        viewModel = CharactersViewModel(
            cc = Dispatchers.Main,
            repo = repo!!,
            domainCharacterToUICharacter = domainCharacterToUICharacter,
            offset = 0
        )
    }

    @Test
    fun `Init state is loading`(): Unit = runTest {
        server!!.enqueueSuccess()
        val result = viewModel!!.loading.first()
        assert(result)
    }

    @Test
    fun `Characters list should emits EMPTY first`(): Unit = runTest {
        server!!.enqueueSuccess()
        viewModel!!.charactersList(0)
        val result = viewModel!!.result.first()
        assert(result == UICharacterList.EMPTY)
    }

    @Test
    fun `Characters list should emits non empty characters list`(): Unit = runTest {
        server!!.enqueueSuccess()
        viewModel!!.charactersList(0)
        val result = viewModel!!.result.drop(1).first()
        assert(result.items.isNotEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel = null
        repo = null
        server!!.shutdown()
        server = null
    }

    inner class FakeDB : IDB {
        override fun init(context: Context) {}
        override fun putCharacters(characters: List<DBCharacter>) {}
        override fun getCharacters(offset: Int): List<DBCharacter> = emptyList()
        override fun close() {}
    }
}