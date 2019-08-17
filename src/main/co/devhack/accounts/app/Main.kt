package co.devhack.accounts.app

import co.devhack.accounts.app.data.db.DatabaseFactory
import co.devhack.accounts.app.data.repositories.AccountRepository
import co.devhack.accounts.app.endpoints.account
import co.devhack.accounts.app.usecases.account.CreateAccount
import co.devhack.accounts.app.usecases.account.GetAccountById
import co.devhack.accounts.app.usecases.account.GetAllAccounts
import com.codahale.metrics.JmxReporter
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.metrics.dropwizard.DropwizardMetrics
import io.ktor.metrics.micrometer.MicrometerMetrics
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import java.util.concurrent.TimeUnit

fun Application.account() {

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(MicrometerMetrics) {
        registry = SimpleMeterRegistry()
        meterBinders = listOf(
            ClassLoaderMetrics(),
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            ProcessorMetrics(),
            JvmThreadMetrics(),
            FileDescriptorMetrics()
        )
    }

    install(DropwizardMetrics) {
        JmxReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .start()
    }

    DatabaseFactory.init()

    val createAccount: CreateAccount by lazy {
        CreateAccount(AccountRepository())
    }

    val getAllAccounts: GetAllAccounts by lazy {
        GetAllAccounts(AccountRepository())
    }

    val getAccountById: GetAccountById by lazy {
        GetAccountById(AccountRepository())
    }

    install(Routing) {
        account(
            createAccount,
            getAllAccounts,
            getAccountById
        )
    }

}

fun main() {
    embeddedServer(
        Netty,
        3939,
        watchPaths = listOf("MainKt"),
        module = Application::account
    )
        .start()
}