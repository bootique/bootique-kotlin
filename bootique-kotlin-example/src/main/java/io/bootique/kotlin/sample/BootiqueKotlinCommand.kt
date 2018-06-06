/**
 *    Licensed to the ObjectStyle LLC under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ObjectStyle LLC licenses
 *  this file to you under the Apache License, Version 2.0 (the
 *  “License”); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.bootique.kotlin.sample

import com.google.inject.Binder
import com.google.inject.Inject
import com.google.inject.Provider
import io.bootique.BQCoreModule
import io.bootique.ConfigModule
import io.bootique.cli.Cli
import io.bootique.command.CommandOutcome
import io.bootique.command.CommandWithMetadata
import io.bootique.kotlin.extra.addCommand
import io.bootique.kotlin.extra.bootiqueCommand
import io.bootique.kotlin.guice.KotlinBinder
import io.bootique.kotlin.guice.KotlinModule

class AppCommandModule : KotlinModule, ConfigModule() {
    override fun configure(binder: KotlinBinder) {
        binder.bind(CommandRunner::class).to(CommandRunnerImpl::class)
    }

    override fun configure(binder: Binder) {
        BQCoreModule
            .extend(binder)
            .addCommand(AppCommand::class)
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
    private val runner: Provider<CommandRunner>
) : CommandWithMetadata(commandMetadata) {

    override fun run(cli: Cli): CommandOutcome {
        runner.get().run()
        return CommandOutcome.succeeded()
    }
}

private val commandMetadata = bootiqueCommand<AppCommand> {
    description("This command starts application.")
}
