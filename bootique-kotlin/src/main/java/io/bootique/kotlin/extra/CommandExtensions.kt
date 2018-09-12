/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.kotlin.extra

import io.bootique.BQCoreModuleExtender
import io.bootique.command.Command
import io.bootique.command.CommandDecorator
import io.bootique.meta.application.CommandMetadata
import kotlin.reflect.KClass

/**
 * Helper function to contribute command using [KClass].
 *
 * @author Ruslan Ibragimov
 * @since 0.1
 */
fun BQCoreModuleExtender.addCommand(
    commandType: KClass<out Command>
): BQCoreModuleExtender {
    return this.addCommand(commandType.java)
}

/**
 * Helper function to contribute default command using [KClass].
 *
 * @author Ruslan Ibragimov
 * @since 0.1
 */
fun BQCoreModuleExtender.setDefaultCommand(
    commandType: KClass<out Command>
): BQCoreModuleExtender {
    return this.setDefaultCommand(commandType.java)
}

/**
 * Helper function to create command metadata in DSL-style.
 *
 * @author Ruslan Ibragimov
 * @since 0.1
 */
inline fun <reified T : Command> bootiqueCommand(
    block: CommandMetadata.Builder.() -> Unit
): CommandMetadata {
    val builder = CommandMetadata.builder(T::class.java)
    block(builder)
    return builder.build()
}

/**
 * Helper function to decorate command using [KClass].
 *
 * @author Ruslan Ibragimov
 * ## 1.0.RC1
 */
fun BQCoreModuleExtender.decorateCommand(
    commandType: KClass<out Command>,
    commandDecorator: CommandDecorator
): BQCoreModuleExtender {
    return this.decorateCommand(commandType.java, commandDecorator)
}

