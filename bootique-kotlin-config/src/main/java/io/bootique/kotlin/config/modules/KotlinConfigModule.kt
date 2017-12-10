package io.bootique.kotlin.config.modules

import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.config.DefaultKotlinScriptCompiler
import io.bootique.kotlin.config.KotlinScriptCompiler
import io.bootique.kotlin.config.KotlinScriptConfigurationFactory
import io.bootique.kotlin.guice.KotlinBinder
import io.bootique.kotlin.guice.KotlinModule

/**
 * Module for overriding default JsonNodeConfigurationFactory.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
class KotlinConfigModule : KotlinModule {
    override fun configure(binder: KotlinBinder) {
        binder.bind(KotlinScriptCompiler::class).to(DefaultKotlinScriptCompiler::class).asSingleton()
        binder.bind(ConfigurationFactory::class).to(KotlinScriptConfigurationFactory::class).asSingleton()
    }
}
