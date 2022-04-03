package kady.muhammad.paytabstask.data.db

import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import io.objectbox.kotlin.boxFor
import kady.muhammad.paytabstask.BuildConfig
import java.io.File

class DB(private val pageLimit: Int) : IDB {

    private lateinit var store: BoxStore

    override fun init(path: File) {
        store = MyObjectBox.builder()
            .directory(path)
            .apply {
                if (BuildConfig.DEBUG) {
                    debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
                }
            }
            .build()
    }

    override fun putCharacters(characters: List<DBCharacter>) {
        store.boxFor<DBCharacter>().put(characters)
    }

    override fun getCharacters(page: Int): List<DBCharacter> {
        val upper = page * pageLimit
        val lower = if (page == 1) 1 else (upper - pageLimit) + 1
        println("page = $page lower = $lower upper = $upper")
        return store
            .boxFor<DBCharacter>()
            .query().between(DBCharacter_.dbId, lower.toLong(), upper.toLong()).build()
            .find()
    }

    override fun close() {
        store.close()
    }

}