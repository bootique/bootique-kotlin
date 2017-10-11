package io.bootique.kotlin.sample

import io.bootique.Bootique
import io.bootique.kotlin.config.withKotlinConfig

fun main(args: Array<String>) {
    Bootique.app("--config=classpath:config.kts", "--config=classpath:config1.kts", "--server")
        .withKotlinConfig()
        .autoLoadModules()
        .exec()
        .exit()
}

data class TestConfig(val name: String, val products: List<String>)
