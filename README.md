[![Build Status](https://travis-ci.org/bootique/bootique-kotlin.svg)](https://travis-ci.org/bootique/bootique-kotlin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.bootique.kotlin/bootique-kotlin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.bootique.kotlin/bootique-kotlin/)

# Bootique Kotlin

Provides extension function and features for smooth development with Bootique and Kotlin.

## Kotlin

This project supports Kotlin 1.1.

## Setup

```gradle
compile("io.bootique.kotlin:bootique-kotlin:0.1")
compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.0-rc-91")
```

## Overview

This project contains two parts:

1. Extension functions
2. Kotlin Script Configuration Module

### Configuration Module

Use Kotlin Script for configuration really simple:

1. Create script
2. Override `ConfigurationFactory`

#### Configuration with Kotlin can be defined in Kotlin Script file:

```kotlin
import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.config.modules.httpConnector
import io.bootique.kotlin.config.modules.jetty

config {
    jetty {
        httpConnector {
            port = 4242
            host = "0.0.0.0"
        }
    }
}
```

#### Enable Kotlin Script Configuration in Bootique:

```kotlin
fun main(args: Array<String>) {
    Bootique.app(*args)
        .withKotlinConfig() // Extension function
        .autoLoadModules()
        .run()
}
```

You can pass this file as always to bootique:

```bash
java -jar app.jar --config=classpath:config.kts --server
```

It's even support multiple files (each file contains map of configs):

```bash
java -jar app.jar --config=classpath:config.kts --config=classpath:config1.kts --server
```

That's it! You get autocompletion in IDE, and **code** for configuration!

### Extension Function in Action

Bootstrap application:

```kotlin
fun main(args: Array<String>) {
    Bootique
        .app(*args)
        .module<LogbackModule>()
        .module(LogbackModule::class)
        .modules(LogbackModule::class, JettyModule::class)
        .autoLoadModules()
}
```

Bindings:

*We use `toClass` instead of `to` because `to` is extension on `Any?`, and this can lead to errors.*

```kotlin
class AppCommandModule : ConfigModule() {
    override fun configure(binder: Binder) {
        BQCoreModule
            .extend(binder)
            .addCommand(AppCommand::class)

        binder.bind(CommandRunner::class).toClass(CommandRunnerImpl::class)
        binder.bind<CommandRunner>().toClass<CommandRunnerImpl>()
    }
}
```

Configuration:

```kotlin
class KotlinConfigModule : ConfigModule() {

    @Singleton
    @Provides
    fun createAppConfiguration(configurationFactory: ConfigurationFactory): AppConfiguration {
        return configurationFactory.config(configPrefix)
    }
}
```

Command metadata:

```kotlin
private val commandMetadata = bootiqueCommand<AppCommand> {
    description("This command starts application.")
}
```

## WIP

* We still adding DSL for existing modules
* There are lack of tests
* Kotlin Script Startup time can be optimized
