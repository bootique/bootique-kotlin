package io.bootique.kotlin.core

import com.google.inject.Module
import io.bootique.BQModuleProvider
import java.lang.reflect.Type
import kotlin.reflect.KClass


/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinBQModuleProvider: BQModuleProvider {
    val module: Module

    override fun module(): Module {
        return module
    }

    val configs: Map<String, Type>
        get() = mapOf()

    override fun configs(): Map<String, Type> {
        return configs
    }

    /**
     * Property for more native to Kotlin usage.
     */
    val name: String
        get() = ""

    override fun name(): String {
        return name
    }

    /**
     * Provide property instead of function to use [KClass] instead of [Class].
     */
    val overrides: Collection<KClass<out Module>>
        get() = listOf()

    override fun overrides(): Collection<Class<out Module>> {
        return overrides.map { it.java }
    }

    /**
     * Property for more native to Kotlin usage.
     */
    val dependencies: Collection<BQModuleProvider>
        get() = listOf()

    override fun dependencies(): Collection<BQModuleProvider> {
        return dependencies
    }
}