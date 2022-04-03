package kady.muhammad.paytabstask.data.db

import android.content.Context

interface IDB {
    fun init(context: Context)
    fun putCharacters(characters: List<DBCharacter>)
    fun getCharacters(offset: Int): List<DBCharacter>
    fun close()
}