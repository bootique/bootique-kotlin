package io.bootique.kotlin.config.undertow

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class UndertowModuleTest {

    @Test
    fun undertowTestConfig() {
        val config = undertowFactory(
            httpListeners = listOf(
                httpListener(8080)
            ),
            directBuffers = true
        )

        assertEquals(8080, config.httpListeners[0].port)
        assertEquals("0.0.0.0", config.httpListeners[0].host)
        assertEquals(true, config.directBuffers)
    }
}
