package io.bootique.kotlin

import io.bootique.kotlin.config.modules.config
import org.junit.Assert.assertTrue
import org.junit.Test

internal data class Sample(val text: String)

class ConfigurationTest {

    @Test fun test() {
        val config = config {
            addConfig("Yoda" to Sample("Jedi"))
        }

        assertTrue(config["Yoda"] == Sample("Jedi"))
    }

}