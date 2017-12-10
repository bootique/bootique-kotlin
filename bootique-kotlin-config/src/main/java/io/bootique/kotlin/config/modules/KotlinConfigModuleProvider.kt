package io.bootique.kotlin.config.modules

import io.bootique.BQCoreModule
import io.bootique.BQModuleProvider
import io.bootique.kotlin.core.KotlinBQModuleProvider

/**
 * [BQModuleProvider] for [KotlinConfigModule].
 *
 * @author Ibragimov Ruslan
 * @since 0.5
 */
class KotlinConfigModuleProvider : KotlinBQModuleProvider {
    override val module = KotlinConfigModule()
    override val overrides = listOf(BQCoreModule::class)
}
