package io.bootique.kotlin.sample

import com.google.inject.Provides
import com.google.inject.Singleton
import io.bootique.ConfigModule
import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.extra.config

data class AppConfiguration(
    val name: String
)

class KotlinConfigModule : ConfigModule() {

    @Singleton
    @Provides
    fun createAppConfiguration(configurationFactory: ConfigurationFactory): AppConfiguration {
        return configurationFactory.config(configPrefix)
    }
}

