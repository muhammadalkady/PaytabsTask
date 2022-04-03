package kady.muhammad.paytabstask.data.db

import java.io.File

interface IDB {
    fun init(path: File)
    fun putCharacters(characters: List<DBCharacter>)
    fun getCharacters(page: Int): List<DBCharacter>
    fun close()
}