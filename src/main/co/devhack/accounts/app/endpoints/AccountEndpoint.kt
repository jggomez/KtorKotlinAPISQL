package co.devhack.accounts.app.endpoints

import co.devhack.accounts.app.base.Failure
import co.devhack.accounts.app.base.UseCase
import co.devhack.accounts.app.endpoints.dto.CreateAccountRespond
import co.devhack.accounts.app.endpoints.dto.ErrorApp
import co.devhack.accounts.app.usecases.account.CreateAccount
import co.devhack.accounts.app.usecases.account.GetAccountById
import co.devhack.accounts.app.usecases.account.GetAllAccounts
import co.devhack.accounts.app.usecases.domain.Account
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.account(
    createAccount: CreateAccount,
    getAllAccounts: GetAllAccounts,
    getAccountById: GetAccountById
) {

    route("/account") {

        post("/") {
            val account = call.receive<Account>()
            val resp = createAccount.run(CreateAccount.Params(account))

            resp.either({
                when (it) {
                    is Failure.ServerError -> {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            ErrorApp(it.ex.message!!)
                        )
                    }
                }
            })
            { id ->
                call.respond(
                    HttpStatusCode.Created,
                    CreateAccountRespond(id)
                )
            }
        }

        get("/") {
            val resp = getAllAccounts.run(UseCase.None())

            resp.either({
                when (it) {
                    is Failure.ServerError -> {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            ErrorApp(it.ex.message!!)
                        )
                    }
                }
            }) {
                call.respond(
                    HttpStatusCode.OK,
                    it
                )
            }
        }

        get("/{id}") {
            val resp = getAccountById.run(GetAccountById.Params(call.parameters["id"]?.toInt()!!))
            resp.either({
                when (it) {
                    is Failure.ServerError -> {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            ErrorApp(it.ex.message!!)
                        )
                    }
                }
            }) {
                call.respond(
                    HttpStatusCode.OK,
                    it
                )
            }
        }

    }


}