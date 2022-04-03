package kady.muhammad.paytabstask.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DBCharacter(@Id var dbId: Long = 0, val id: Int, var image: String, var name: String)