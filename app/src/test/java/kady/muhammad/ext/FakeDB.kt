package kady.muhammad.ext

import android.content.Context
import kady.muhammad.paytabstask.data.db.DBCharacter
import kady.muhammad.paytabstask.data.db.IDB

class FakeDB : IDB {
    override fun init(context: Context) {}
    override fun putCharacters(characters: List<DBCharacter>) {}
    override fun getCharacters(offset: Int): List<DBCharacter> = emptyList()
    override fun close() {}
}