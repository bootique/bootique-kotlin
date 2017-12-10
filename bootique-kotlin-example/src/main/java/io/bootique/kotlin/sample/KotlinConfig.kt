package io.bootique.kotlin.sample

import io.bootique.kotlin.config.withKotlinConfig
import io.bootique.kotlin.core.KotlinBootique

fun main(args: Array<String>) {
    KotlinBootique(arrayOf("--config=classpath:config.kts", "--config=classpath:config1.kts", "--server"))
        .withKotlinConfig()
        .autoLoadModules()
        .exec()
        .exit()
}

data class TestConfig(val name: String, val products: List<String>)
