package io.bootique.kotlin

import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.binder.AnnotatedBindingBuilder
import com.google.inject.binder.LinkedBindingBuilder
import com.google.inject.binder.ScopedBindingBuilder
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
fun <T : Any> LinkedBindingBuilder<T>.to(implementation: KClass<out T>): ScopedBindingBuilder {
    return this.to(implementation.java)
}

inline fun <reified T : Any> LinkedBindingBuilder<in T>.to(): ScopedBindingBuilder {
    return this.to(T::class.java)
}

fun <T : Any> Binder.bind(type: KClass<T>): AnnotatedBindingBuilder<T> {
    return this.bind(type.java)
}

inline fun <reified T : Any> Binder.bind(): AnnotatedBindingBuilder<T> {
    return this.bind(T::class.java)
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
