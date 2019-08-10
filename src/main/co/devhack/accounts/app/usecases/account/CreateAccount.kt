package co.devhack.accounts.app.usecases.account

import co.devhack.accounts.app.base.Either
import co.devhack.accounts.app.base.Failure
import co.devhack.accounts.app.base.UseCase
import co.devhack.accounts.app.usecases.domain.Account
import co.devhack.accounts.app.usecases.repositories.IAccountRepository

class CreateAccount(
    private val accountRepository: IAccountRepository
) : UseCase<Int, CreateAccount.Params>() {

    override suspend fun run(params: Params): Either<Failure, Int> {
        return accountRepository.insert(params.account)
    }

    data class Params(val account: Account)
}