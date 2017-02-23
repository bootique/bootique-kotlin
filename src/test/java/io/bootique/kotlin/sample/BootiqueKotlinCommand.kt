package io.bootique.kotlin.sample

import com.google.inject.Binder
import com.google.inject.Inject
import com.google.inject.Provider
import io.bootique.BQCoreModule
import io.bootique.ConfigModule
import io.bootique.cli.Cli
import io.bootique.command.CommandOutcome
import io.bootique.command.CommandWithMetadata
import io.bootique.kotlin.bind
import io.bootique.kotlin.bootiqueCommand
import io.bootique.kotlin.to

class AppCommandModule : ConfigModule() {
    override fun configure(binder: Binder) {
        BQCoreModule
            .contributeCommands(binder)
            .addBinding()
            .to(AppCommand::class)

        binder.bind(CommandRunner::class).to(CommandRunnerImpl::class)
        binder.bind<CommandRunner>().to<CommandRunnerImpl>()
    }
}

interface CommandRunner {
    fun run()
}

class CommandRunnerImpl : CommandRunner {
    override fun run() {

    }
}

class AppCommand @Inject constructor(
    val runner: Provider<CommandRunner>
) : CommandWithMetadata(commandMetadata) {

    override fun run(cli: Cli): CommandOutcome {
        runner.get().run()
        return CommandOutcome.succeeded()
    }
}

private val commandMetadata = bootiqueCommand<AppCommand> {
    description("This command starts application.")
}
