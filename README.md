[![Build Status](https://travis-ci.org/bootique/bootique-kotlin.svg)](https://travis-ci.org/bootique/bootique-kotlin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.bootique.kotlin/bootique-kotlin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.bootique.kotlin/bootique-kotlin/)

# Bootique Kotlin

Provides extension function and features for smooth development with [Bootique](http://bootique.io/) and [Kotlin](http://kotlinlang.org/).

## Kotlin

Kotlin 1.1.x used in project.

## Setup

```gradle
// Kotlin Extensions for Bootique
compile("io.bootique.kotlin:bootique-kotlin:0.4-SNAPSHOT")

// Kotlin Configuration Module
compile("io.bootique.kotlin:bootique-kotlin-config:0.4-SNAPSHOT")

// Kotlin Configuration and Extensions for Jetty. Also this adds dependency to bootique-jetty module.
compile("io.bootique.kotlin:bootique-kotlin-jetty:0.4-SNAPSHOT")

// Kotlin Configuration and Extensions for Logback. Also this adds dependency to bootique-logback module.
compile("io.bootique.kotlin:bootique-kotlin-logback:0.4-SNAPSHOT")

// Kotlin Configuration and Extensions for $moduleName$. Also this adds dependency to bootique-$moduleName$ module.
compile("io.bootique.kotlin:bootique-kotlin-$moduleName$:0.4-SNAPSHOT")
```

## Overview

This project contains three main parts:

1. Extension functions for Bootique
2. Kotlin Script Configuration Module
3. Configuration and Extensions for Modules

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

### Extension Functions in Action:

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
