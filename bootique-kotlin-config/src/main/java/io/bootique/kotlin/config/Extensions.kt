package io.bootique.kotlin.config

import io.bootique.BQCoreModule
import io.bootique.Bootique
import io.bootique.kotlin.config.modules.KotlinConfigModule
import io.bootique.kotlin.core.KotlinBootiqueInterface

/**
 * Extensions for config bootstrapping.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
fun Bootique.withKotlinConfig(): Bootique {
    return this.override(BQCoreModule::class.java)
        .with(KotlinConfigModule::class.java)
}

fun KotlinBootiqueInterface.withKotlinConfig(): KotlinBootiqueInterface {
    return this.override(BQCoreModule::class).with(KotlinConfigModule::class)
}
