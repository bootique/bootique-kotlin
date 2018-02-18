package io.bootique.kotlin.sample

import com.google.inject.Inject
import com.google.inject.Provider
import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.config.withKotlinConfig
import io.bootique.kotlin.core.KotlinBootique
import io.bootique.kotlin.extra.config
import io.bootique.kotlin.guice.KotlinBinder
import io.bootique.kotlin.guice.KotlinModule

fun main(args: Array<String>) {
    val instance = KotlinBootique(arrayOf("--config=classpath:config.kts", "--config=classpath:config1.kts", "--server"))
        .withKotlinConfig()
        .autoLoadModules()
        .module(ApplicationModule::class)
        .createRuntime()
        .getInstance(TestConfig::class)

    println(instance)
}

class ApplicationModule : KotlinModule {
    override fun configure(binder: KotlinBinder) {
        binder.bind(TestConfig::class).toProvider(TestConfigProvider::class)
    }
}

class TestConfigProvider @Inject constructor(
    private val configurationFactory: ConfigurationFactory
) : Provider<TestConfig> {
    override fun get(): TestConfig {
        return configurationFactory.config("test")
    }
}

data class TestConfig(
    val name: String,
    val products: List<String>
)
