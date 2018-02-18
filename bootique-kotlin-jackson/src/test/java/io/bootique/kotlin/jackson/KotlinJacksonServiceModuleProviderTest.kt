package io.bootique.kotlin.jackson

import io.bootique.test.junit.BQModuleProviderChecker
import org.junit.Test

/**
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class KotlinJacksonServiceModuleProviderTest {

    @Test
    fun testAutoLoadable() {
        BQModuleProviderChecker
            .testAutoLoadable(KotlinJacksonServiceModuleProvider::class.java)
    }
}
