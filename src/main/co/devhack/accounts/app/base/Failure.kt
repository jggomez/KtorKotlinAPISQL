package co.devhack.accounts.app.base

sealed class Failure {

    object NetworkConnection : Failure()
    class ServerError(val ex: Exception) : Failure()
    object CustomError : Failure()

    abstract class FeatureFailure : Failure()

}