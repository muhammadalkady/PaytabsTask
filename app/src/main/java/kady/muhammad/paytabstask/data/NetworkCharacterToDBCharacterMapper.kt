package kady.muhammad.paytabstask.data

import kady.muhammad.paytabstask.app.IMapper
import kady.muhammad.paytabstask.data.db.DBCharacter
import kady.muhammad.paytabstask.data.network.DataCharacters

class NetworkCharacterToDBCharacterMapper : IMapper<DataCharacters.Data.Character, DBCharacter> {
    override fun map(input: DataCharacters.Data.Character): DBCharacter {
        val url = "${input.thumbnail.path}.${input.thumbnail.extension}"
        return DBCharacter(id = input.id, image = url, name = input.name)
    }
}