package co.devhack.accounts.app.usecases.account

import co.devhack.accounts.app.base.Either
import co.devhack.accounts.app.base.Failure
import co.devhack.accounts.app.base.UseCase
import co.devhack.accounts.app.usecases.domain.Account
import co.devhack.accounts.app.usecases.repositories.IAccountRepository

class GetAccountById(
    private val accountRepository: IAccountRepository
) : UseCase<Account, GetAccountById.Params>() {

    override suspend fun run(params: Params): Either<Failure, Account> {
        return accountRepository.getById(params.id)
    }

    data class Params(val id: Int)
}