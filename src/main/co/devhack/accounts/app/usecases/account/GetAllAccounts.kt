package co.devhack.accounts.app.usecases.account

import co.devhack.accounts.app.base.Either
import co.devhack.accounts.app.base.Failure
import co.devhack.accounts.app.base.UseCase
import co.devhack.accounts.app.usecases.domain.Account
import co.devhack.accounts.app.usecases.repositories.IAccountRepository

class GetAllAccounts(
    private val accountRepository: IAccountRepository
) : UseCase<List<Account>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Account>> {
        return accountRepository.getAll()
    }

}