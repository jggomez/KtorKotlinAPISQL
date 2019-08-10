package co.devhack.accounts.app.base

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    suspend fun execute(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val resp = run(params)
        onResult(resp)
    }

    class None
}