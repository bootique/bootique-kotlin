package io.bootique.kotlin

import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.Scope
import com.google.inject.Singleton
import com.google.inject.binder.AnnotatedBindingBuilder
import com.google.inject.binder.LinkedBindingBuilder
import com.google.inject.binder.ScopedBindingBuilder
import io.bootique.BQCoreModuleExtender
import io.bootique.Bootique
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
// Extras
inline fun <reified C : Any> ConfigurationFactory.config(prefix: String): C {
    return this.config(C::class.java, prefix)
}

inline fun <reified T : Command> bootiqueCommand(block: Builder.() -> Unit): CommandMetadata {
    val builder = CommandMetadata.builder(T::class.java)
    block(builder)
    return builder.build()
}

// Guice
/**
 * Rename from `to` to `toClass` since kotlin has built
 * in extension function `to` for creating tuples.
 */
fun <T : Any> LinkedBindingBuilder<T>.toClass(implementation: KClass<out T>): ScopedBindingBuilder {
    return this.to(implementation.java)
}

/**
 * Rename from `to` to `toClass` since kotlin has built
 * in extension function `to` for creating tuples.
 */
inline fun <reified T : Any> LinkedBindingBuilder<in T>.toClass(): ScopedBindingBuilder {
    return this.to(T::class.java)
}

fun BQCoreModuleExtender.addCommand(commandType: KClass<out Command>): BQCoreModuleExtender {
    return this.addCommand(commandType.java)
}

fun BQCoreModuleExtender.setDefaultCommand(commandType: KClass<out Command>): BQCoreModuleExtender {
    return this.setDefaultCommand(commandType.java)
}

fun <T : Any> Binder.bind(type: KClass<T>): AnnotatedBindingBuilder<T> {
    return this.bind(type.java)
}

inline fun <reified T : Any> Binder.bind(): AnnotatedBindingBuilder<T> {
    return this.bind(T::class.java)
}

fun ScopedBindingBuilder.asSingleton() {
    this.inScope(Singleton::class)
}

fun ScopedBindingBuilder.inScope(scopeAnnotation: KClass<out Annotation>) {
    this.`in`(scopeAnnotation.java)
}

fun ScopedBindingBuilder.inScope(scope: Scope) {
    this.`in`(scope)
}

// Bootique
fun Bootique.module(moduleClass: KClass<out Module>): Bootique {
    return this.module(moduleClass.java)
}

fun Bootique.modules(vararg modulesClasses: KClass<out Module>): Bootique {
    modulesClasses.forEach { this.module(it) }
    return this
}

inline fun <reified T : Module> Bootique.module(): Bootique {
    return this.module(T::class.java)
}
