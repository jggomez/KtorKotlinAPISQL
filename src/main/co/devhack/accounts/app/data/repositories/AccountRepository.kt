package co.devhack.accounts.app.data.repositories

import co.devhack.accounts.app.base.Either
import co.devhack.accounts.app.base.Failure
import co.devhack.accounts.app.data.db.DatabaseFactory
import co.devhack.accounts.app.data.entities.AccountEntity
import co.devhack.accounts.app.usecases.domain.Account
import co.devhack.accounts.app.usecases.repositories.IAccountRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class AccountRepository : IAccountRepository {

    override suspend fun getById(id: Int): Either<Failure, Account> {
        var account: Account? = null

        try {
            DatabaseFactory.dbQuery {

                account = AccountEntity.select {
                    (AccountEntity.id eq id)
                }.mapNotNull { toAccount(it) }
                    .singleOrNull()
            }
        } catch (ex: Exception) {
            return Either.Left(Failure.ServerError(ex))
        }

        return Either.Right(account!!)
    }

    override suspend fun insert(account: Account): Either<Failure, Int> {
        var id: Int? = 0
        try {
            DatabaseFactory.dbQuery {
                id = AccountEntity.insert {
                    it[description] = account.description
                    it[dateCreated] = account.dateCreated
                    it[money] = account.money
                } get AccountEntity.id
            }
        } catch (ex: Exception) {
            return Either.Left(Failure.ServerError(ex))
        }

        return Either.Right(id ?: 0)
    }

    override suspend fun getAll(): Either<Failure, List<Account>> {
        var accounts: List<Account> = emptyList()

        try {
            DatabaseFactory.dbQuery {
                accounts = AccountEntity.selectAll()
                    .map {
                        toAccount(it)
                    }
            }
        } catch (ex: Exception) {
            return Either.Left(Failure.ServerError(ex))
        }

        return Either.Right(accounts)
    }

    private fun toAccount(row: ResultRow) =
        Account(
            numDocument = row[AccountEntity.id],
            description = row[AccountEntity.description],
            dateCreated = row[AccountEntity.dateCreated],
            money = row[AccountEntity.money]
        )

}