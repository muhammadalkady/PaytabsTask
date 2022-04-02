package kady.muhammad.paytabstask.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DBCharacter(@Id(assignable = true) var id: Long, var image: String, var name: String)