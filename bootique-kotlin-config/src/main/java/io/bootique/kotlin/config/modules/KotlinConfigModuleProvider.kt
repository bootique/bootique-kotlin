package io.bootique.kotlin.config.modules

import com.google.inject.Module
import io.bootique.BQCoreModule
import io.bootique.BQModuleProvider

/**
 * [BQModuleProvider] for [KotlinConfigModule].
 *
 * @author Ibragimov Ruslan
 * @since 0.5
 */
class KotlinConfigModuleProvider : BQModuleProvider {
    override fun module(): Module = KotlinConfigModule()

    override fun overrides(): MutableCollection<Class<out Module>> {
        return mutableListOf(BQCoreModule::class.java)
    }
}
