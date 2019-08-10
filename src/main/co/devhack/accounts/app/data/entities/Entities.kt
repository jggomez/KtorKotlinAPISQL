package co.devhack.accounts.app.data.entities

import org.jetbrains.exposed.sql.Table

object AccountEntity : Table("accountktor") {
    val id = integer("id").primaryKey().autoIncrement()
    val description = varchar("name", 255)
    val dateCreated = varchar("dateCreated", 20)
    val money = double("money")
}