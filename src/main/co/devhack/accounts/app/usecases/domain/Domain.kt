package co.devhack.accounts.app.usecases.domain

data class Account(
    val numDocument: Int,
    val description : String,
    val dateCreated: String,
    val money: Double
)