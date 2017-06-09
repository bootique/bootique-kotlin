package io.bootique.kotlin

import io.bootique.jetty.server.ServerFactory
import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.config.modules.httpConnector
import io.bootique.kotlin.config.modules.jetty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class JettyModuleTest {
    @Test fun singleHttpConnector() {
        val server = config {
            jetty {
                httpConnector {
                    port = 4242
                    host = "192.168.0.1"
                    responseHeaderSize = 42
                    requestHeaderSize = 13
                }
            }
        }

        val obj = (server["jetty"] as ServerFactory).connectors[0]

        assertEquals(4242, obj.port)
        assertEquals("192.168.0.1", obj.host)
        assertEquals(42, obj.responseHeaderSize)
        assertEquals(13, obj.requestHeaderSize)
    }

    @Test fun jettyConfig() {
        val config = config {
            jetty {

            }
        }

        assertNotNull(config["jetty"])
    }
}