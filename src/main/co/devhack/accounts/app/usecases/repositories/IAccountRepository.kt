package co.devhack.accounts.app.usecases.repositories

import co.devhack.accounts.app.base.Either
import co.devhack.accounts.app.base.Failure
import co.devhack.accounts.app.usecases.domain.Account

interface IAccountRepository {

    suspend fun insert(account: Account): Either<Failure, Int>

    suspend fun getById(id: Int): Either<Failure, Account>

    suspend fun getAll(): Either<Failure, List<Account>>
}