package io.bootique.kotlin.config

import io.bootique.kotlin.config.modules.KotlinConfigModuleProvider
import io.bootique.test.junit.BQModuleProviderChecker
import org.junit.Test

/**
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class KotlinConfigModuleProviderTest {

    @Test
    fun testAutoLoadable() {
        BQModuleProviderChecker
            .testAutoLoadable(KotlinConfigModuleProvider::class.java)
    }
}
