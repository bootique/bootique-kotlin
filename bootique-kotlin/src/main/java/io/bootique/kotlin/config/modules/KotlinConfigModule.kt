package io.bootique.kotlin.config.modules

import com.google.inject.Binder
import com.google.inject.Module
import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.asSingleton
import io.bootique.kotlin.bind
import io.bootique.kotlin.config.DefaultKotlinScriptCompiler
import io.bootique.kotlin.config.KotlinScriptCompiler
import io.bootique.kotlin.config.KotlinScriptConfigurationFactory
import io.bootique.kotlin.toClass

/**
 * Module for overriding default JsonNodeConfigurationFactory.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
class KotlinConfigModule : Module {
    override fun configure(binder: Binder) {
        binder.bind(KotlinScriptCompiler::class).toClass(DefaultKotlinScriptCompiler::class).asSingleton()
        binder.bind(ConfigurationFactory::class).toClass(KotlinScriptConfigurationFactory::class).asSingleton()
    }
}