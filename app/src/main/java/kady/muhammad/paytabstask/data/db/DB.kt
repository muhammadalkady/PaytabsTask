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

    override fun getCharacters(offset: Int): List<DBCharacter> {
        val upper = (offset + 1) * pageLimit
        val lower = upper - (pageLimit - 1)
        return store
            .boxFor<DBCharacter>()
            .query()
            .between(DBCharacter_.__ID_PROPERTY, lower.toLong(), upper.toLong())
            .build()
            .find()
    }

    override fun close() {
        store.close()
    }

}