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

## Usage

Bootstrap application:

```kotlin
fun main(args: Array<String>) {
    bootique(args) {
        module<LogbackModule>()
        module(FlywayModule::class)
        modules(UndertowModule::class, CayenneModule::class)
        autoLoadModules()
    }
}
```

Bindings:

```kotlin
class AppCommandModule : ConfigModule() {
    override fun configure(binder: Binder) {
        BQCoreModule
            .contributeCommands(binder)
            .addBinding()
            .to(AppCommand::class)

        binder.bind(CommandRunner::class).to(CommandRunnerImpl::class)
        binder.bind<CommandRunner>().to<CommandRunnerImpl>()
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