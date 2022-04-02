package kady.muhammad.paytabstask.data

import kady.muhammad.paytabstask.app.Mapper
import kady.muhammad.paytabstask.data.db.DBCharacter
import kady.muhammad.paytabstask.data.network.DataCharacters

class NetworkCharacterToDBCharacter : Mapper<DataCharacters.Data.Character, DBCharacter> {
    override fun map(input: DataCharacters.Data.Character): DBCharacter {
        val url = "${input.thumbnail.path}.${input.thumbnail.extension}"
        return DBCharacter(input.id.toLong(), url, input.name)
    }

}