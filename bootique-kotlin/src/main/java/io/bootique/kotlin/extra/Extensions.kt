/**
 *  Licensed to ObjectStyle LLC under one
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

package io.bootique.kotlin.extra

import io.bootique.BQCoreModuleExtender
import io.bootique.command.Command
import io.bootique.config.ConfigurationFactory
import io.bootique.meta.application.CommandMetadata
import io.bootique.meta.application.CommandMetadata.Builder
import kotlin.reflect.KClass

/**
 * Number of useful extensions for bootique.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
inline fun <reified T : Command> bootiqueCommand(block: Builder.() -> Unit): CommandMetadata {
    val builder = CommandMetadata.builder(T::class.java)
    block(builder)
    return builder.build()
}

inline fun <reified T : Any> ConfigurationFactory.config(prefix: String): T {
    return this.config(T::class.java, prefix)
}

fun <T : Any> ConfigurationFactory.config(type: KClass<out T>, prefix: String): T {
    return this.config(type.java, prefix)
}

fun BQCoreModuleExtender.addCommand(commandType: KClass<out Command>): BQCoreModuleExtender {
    return this.addCommand(commandType.java)
}

fun BQCoreModuleExtender.setDefaultCommand(commandType: KClass<out Command>): BQCoreModuleExtender {
    return this.setDefaultCommand(commandType.java)
}
