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
