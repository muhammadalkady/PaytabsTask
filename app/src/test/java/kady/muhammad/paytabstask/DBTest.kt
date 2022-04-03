package kady.muhammad.paytabstask

import io.objectbox.BoxStore
import kady.muhammad.ext.FakeRepo
import kady.muhammad.paytabstask.data.db.DB
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.IMarvelAPI
import kady.muhammad.paytabstask.domain.IRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class DBTest {

    private lateinit var db: IDB
    private lateinit var repo: IRepo

    @Before
    fun setUp() {
        Dispatchers.setMain(newFixedThreadPoolContext(1, "Test Thread"))
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        db = DB(IMarvelAPI.LIMIT).apply { init(TEST_DIRECTORY) }
        repo = FakeRepo(db = db)
    }

    @Test
    fun `Get characters from db should return items size = IMarvelAPI LIMIT`() = runTest {
        val offset = 0
        repo.charactersList(offset)
        println(db.getCharacters(offset))
        assert(db.getCharacters(offset).size == IMarvelAPI.LIMIT)
    }

    @Test
    fun `Get characters from db should return items size = IMarvelAPI LIMIT after two subsequent inserts`() =
        runTest {
            val offset = 0
            repo.charactersList(offset)
            repo.charactersList(offset + 1)
            println(db.getCharacters(offset))
            assert(db.getCharacters(offset).size == IMarvelAPI.LIMIT)
        }

    @Test
    fun `Get characters from db with offset = 1 should return 10 items with ids from 11 to 20`() =
        runTest {
            val offset = 0
            val nextOffset = offset + 1
            repo.charactersList(offset)
            repo.charactersList(nextOffset)
            println(db.getCharacters(offset))
            assert(db.getCharacters(nextOffset).size == IMarvelAPI.LIMIT)
            assert(db.getCharacters(nextOffset).map { it.dbId } == (11L..20L).toList())
        }

    @After
    fun tearDown() {
        db.close()
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        Dispatchers.resetMain()
    }

    companion object {
        private val TEST_DIRECTORY = File("objectbox-example/test-db")
    }
}