package io.bootique.kotlin

import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.config.modules.jetty
import io.bootique.kotlin.config.modules.logback
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

internal data class Sample(val text: String)

class ConfigurationTest {

    @Test fun singleConfig() {
        val config = config {
            addConfig("Yoda" to Sample("Jedi"))
        }

        assertEquals(Sample("Jedi"), config["Yoda"])
    }

    @Test fun multipleConfigs() {
        val config = config {
            addConfig("Yoda" to Sample("Jedi"))
            addConfig("Vader" to Sample("Sith"))
        }

        assertEquals(Sample("Jedi"), config["Yoda"])
        assertEquals(Sample("Sith"), config["Vader"])
    }

    @Test fun jettyConfig() {
        val config = config {
            jetty {

            }
        }

        assertNotNull(config["jetty"])
    }

    @Test fun logbackConfig() {
        val config = config {
            logback {

            }
        }

        assertNotNull(config["log"])
    }
}