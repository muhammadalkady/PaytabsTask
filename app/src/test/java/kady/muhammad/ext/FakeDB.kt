package kady.muhammad.ext

import kady.muhammad.paytabstask.data.db.DBCharacter
import kady.muhammad.paytabstask.data.db.IDB
import java.io.File

class FakeDB : IDB {
    override fun init(path: File) {}
    override fun putCharacters(characters: List<DBCharacter>) {}
    override fun getCharacters(offset: Int): List<DBCharacter> = emptyList()
    override fun close() {}
}