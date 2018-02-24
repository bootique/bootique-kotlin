package io.bootique.kotlin.config.undertow

import io.bootique.resource.FolderResourceFactory
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
            staticFiles = listOf(
                staticResource(
                    path = FolderResourceFactory("/root"),
                    url = "/test"
                )
            ),
            directBuffers = true
        )

        assertEquals(8080, config.httpListeners[0].port)
        assertEquals("0.0.0.0", config.httpListeners[0].host)
        assertEquals("/test", config.staticFiles[0].url)
        assertEquals(true, config.directBuffers)
    }
}
