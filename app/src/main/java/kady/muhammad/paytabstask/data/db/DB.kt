package kady.muhammad.paytabstask.data.db

import android.content.Context
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

interface IDB {
    fun init(context: Context)
    fun putCharacters(characters: List<DBCharacter>)
    fun getCharacters(offset: Int): List<DBCharacter>
    fun close()
}

object DB : IDB {

    private lateinit var store: BoxStore

    override fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    override fun putCharacters(characters: List<DBCharacter>) {
        store.boxFor<DBCharacter>().put(characters)
    }

    override fun getCharacters(offset: Int): List<DBCharacter> {
        return store.boxFor<DBCharacter>().all
    }

    override fun close() {
        store.close()
    }

}