package io.bootique.kotlin.config

import io.bootique.BQRuntime
import io.bootique.Bootique
import io.bootique.config.ConfigurationFactory
import io.bootique.jetty.server.ServerHolder
import io.bootique.junit5.BQApp
import io.bootique.value.Bytes
import io.bootique.value.Duration
import io.bootique.value.Percent
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull


class ConfigurationIT {

    @BQApp
    val app: BQRuntime = Bootique
            .app("--server", "--config", "classpath:config-it.bq.kts")
            .autoLoadModules()
            .createRuntime()

    @Test
    fun `test real config`() {
        val serverHolder: ServerHolder = app.getInstance(ServerHolder::class.java)
        val configFactory: ConfigurationFactory = app.getInstance(ConfigurationFactory::class.java)

        assertNotNull(serverHolder)
        assertEquals(4242, serverHolder.connector.port)
        assertEquals("192.168.0.1", serverHolder.connector.host)

        val b = configFactory.config(Bytes::class.java, "test_b")
        assertEquals("50 Kilobytes", b.toString())

        val d = configFactory.config(Duration::class.java, "test_d")
        assertEquals("1s", d.toString())

        val p = configFactory.config(Percent::class.java, "test_p")
        assertEquals("50.0%", p.toString())
    }

}