package io.bootique.kotlin.jackson

import io.bootique.BQCoreModule
import io.bootique.kotlin.core.KotlinBQModuleProvider

/**
 * [KotlinBQModuleProvider] for [KotlinJacksonConfigModule].
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class KotlinJacksonServiceModuleProvider : KotlinBQModuleProvider {
    override val module = KotlinJacksonServiceModule()
    override val overrides = listOf(BQCoreModule::class)
}

