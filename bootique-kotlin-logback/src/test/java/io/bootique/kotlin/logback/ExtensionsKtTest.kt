package io.bootique.kotlin.logback

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class ExtensionsKtTest {
    @Test
    fun testLogger() {
        val logger = logger<ExtensionsKtTest>()
        assertEquals("io.bootique.kotlin.logback.ExtensionsKtTest", logger.name)
    }
}
