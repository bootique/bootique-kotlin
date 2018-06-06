<!--
     Licensed to the ObjectStyle LLC under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ObjectStyle LLC licenses
   this file to you under the Apache License, Version 2.0 (the
   “License”); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.
  -->
# Kotlin Extensions for Bootique and Bootique Modules

## Overview

`bootique-kotlin` contains following modules:

1. Kotlin APIs and extensions for Bootique;
1. Kotlin Script Configuration Module;
1. Configuration and Extensions for Bootique Modules;
1. `JacksonService` which provides `ObjectMapper` with enabled `KotlinModule`.

## TL;DR;

* Use `KotlinBootique` instead of `Bootique`;
* Use `KotlinModule` instead of `Module`, you can use `KotlinModule` with `ConfigModule` (just inherit both);
* Use `KotlinBQModuleProvider` instead of `BQModuleProvider`;
* Use extensions defined in [Extensions.kt](https://github.com/bootique/bootique-kotlin/blob/master/bootique-kotlin/src/main/java/io/bootique/kotlin/extra/Extensions.kt);
* Use `bootique-kotlin-configuration` module to benefit from configuration written in Kotlin.
* Use `bootique-kotlin-jackson` to get `ObjectMapper` with `KotlinModule`.

## Getting started

Kotlin 1.2.x used in project.

Latest stable version: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.bootique.kotlin/bootique-kotlin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.bootique.kotlin/bootique-kotlin/)

```kotlin
// Kotlin Extensions for Bootique
compile("io.bootique.kotlin:bootique-kotlin:0.25")

// Kotlin Configuration Module
compile("io.bootique.kotlin:bootique-kotlin-config:0.25")

// Kotlin Configuration Module
compile("io.bootique.kotlin:bootique-kotlin-jackson:0.25")

// Kotlin Configuration and Extensions for Jetty. Also this adds dependency to bootique-jetty module.
compile("io.bootique.kotlin:bootique-kotlin-jetty:0.25")

// Kotlin Configuration and Extensions for Logback. Also this adds dependency to bootique-logback module.
compile("io.bootique.kotlin:bootique-kotlin-logback:0.25")

// Kotlin Configuration and Extensions for $moduleName$. Also this adds dependency to bootique-$moduleName$ module.
compile("io.bootique.kotlin:bootique-kotlin-$moduleName$:0.25")
```


## Bootique

### Replacement for Bootique

`bootique-kotlin` provides replacement for `Bootique` class - `KotlinBootique`:

```kotlin
fun main(args: Array<String>) {
    KotlinBootique(args)
        .module(ApplicationModule::class)
        .exec()
        .exit()
}
```

So no need for extensions for `Bootique` class, `KotlinBootique` provides best experience for developing Bootique apps with Kotlin.

### KotlinBQModuleProvider

`KotlinBQModuleProvider` - interface to implement in Bootique Kotlin application instead of `BQModuleProvider`.

```kotlin
class ApplicationModuleProvider : KotlinBQModuleProvider {
    override val module = ApplicationModule()
    override val overrides = listOf(BQCoreModule::class)
    override val dependencies = listOf(KotlinConfigModule::class)
}
```

You can see how declarative become module provider.

### Extension: `ConfigurationFactory.config`

```kotlin
// Using Java Api
configurationFactory.config(SampleFactory::class.java, "sample")

// With Extension
configurationFactory.config(SampleFactory::class, "sample")

// With Extension, reified generics
configurationFactory.config<SampleFactory>("sample")

// Type Inference
@Singleton
@Provides
fun createAppConfiguration(configurationFactory: ConfigurationFactory): SampleFactory {
    return configurationFactory.config/* No Type Here */(configPrefix)
}
```

### Extension: `BQCoreModuleExtender.addCommand`

Straightforward and easy to use extension for contributing commands.

```kotlin
BQCoreModule
    .extend(binder)
    .addCommand(ApplicationCommand::class)
```

### Extension: `BQCoreModuleExtender.setDefaultCommand`

Also extension for `setDefaultCommand` available.

```kotlin
BQCoreModule
    .extend(binder)
    .setDefaultCommand(ApplicationCommand::class)
```

### Extensions: 

See [Extensions.kt](https://github.com/bootique/bootique-kotlin/blob/master/bootique-kotlin/src/main/java/io/bootique/kotlin/extra/Extensions.kt) for sources.

### Deprecated Extensions:

These extensions deprecated and deleted in 0.25 in favor of `KotlinModule` and `KotlinBootique`.

* `LinkedBindingBuilder.toClass`
* `ScopedBindingBuilder.asSingleton`
* `ScopedBindingBuilder.inScope`
* `Binder.bind`
* `Bootique.module`
* `Bootique.modules`

## Guice

### KotlinModule

`bootique-kotlin` introduces new module interface to use with kotlin: `KotlinModule`

```kotlin
class ApplicationModule : KotlinModule {
    override fun configure(binder: KotlinBinder) {
        binder.bind(ShareCountService::class).to(DefaultShareCountService::class).asSingleton()
        binder.bind(HttpClient::class).to(DefaultHttpClient::class).asSingleton()
    }
}
```

### Extensions

There are few function to help work with `TypeLiteral` and `Key`.

```kotlin
// TypeLiteral
typeLiteral<Array<String>>()

// Key
key<List<Callable<A>>>()
```

## Configuration Module

Use Kotlin Script for configuration really simple:

1. Create script
2. Override `ConfigurationFactory`

### Configuration with Kotlin can be defined in Kotlin Script file:

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

### Enable Kotlin Script Configuration in Bootique:

With extension:

```kotlin
fun main(args: Array<String>) {
    KotlinBootique(args)
        .withKotlinConfig() // Extension function
        .autoLoadModules()
        .exec()
        .exit()
}
```

Using `BQModuleProvider`:

```kotlin
fun main(args: Array<String>) {
    KotlinBootique(args)
        .module(KotlinConfigModuleProvider())
        .autoLoadModules()
        .exec()
        .exit()
}
```

You can pass this file as always to bootique:

```bash
./bin/application --config=classpath:config.kts --server
```

It's even support multiple files (each file contains map of configs):

```bash
./bin/application --config=classpath:config.kts --config=classpath:config1.kts --server
```

That's it! You get autocomplete in IDE, and **code** for configuration!

## Bootique Jetty

Define empty config:

```kotlin
config {
    jetty {

    }
}
```

Use autocompletion to define configuration.

Use `httpConnector/httpsConnector` extensions to define connectors:

```kotlin
jetty {
    httpConnector {
        port = 4242
        host = "192.168.0.1"
        responseHeaderSize = 42
        requestHeaderSize = 13
    }
}
```

## Bootique Logback

Define empty config:

```kotlin
config {
    logback {

    }
}
```

Number of extensions available inside config:

```kotlin
logback {
    useLogbackConfig = false
    debug = false
    level = LogbackLevel.warn
    logger(FactoryDSL::class, LogbackLevel.error)
    consoleAppender {
        target = ConsoleTarget.stderr
        logFormat = "[%d{dd/MMM/yyyy:HH:mm:ss}] %t %-5p %c{1}: %m%n"
    }
    appender(ConsoleAppenderFactory().apply {
        target = ConsoleTarget.stdout
    })
    fileAppender {
        file = "abc"
        timeBasedRollingPolicy {
            setFileNamePattern("Abc_%d")
        }
        fixedWindowRollingPolicy {
            setFileNamePattern("Abc_%d")
            setFileSize("10mb")
        }
        sizeAndTimeRollingPolicy {
            setFileNamePattern("mylog-%d{yyyy-MM-dd}.%i.txt")
            setFileSize("10mb")
        }
    }
}
```

## Bootique Bom

<!-- TODO -->

## Bootique Undertow

<!-- TODO -->

## Bootique Cayenne

<!-- TODO -->

## Bootique Jdbc

<!-- TODO -->

## Bootique Jersey Client

<!-- TODO -->

## Bootique Linkrest

<!-- TODO -->

## Bootique Mvc

<!-- TODO -->

## Bootique Swagger

<!-- TODO -->

## Bootique Jersey

<!-- TODO -->

## Bootique Shiro

<!-- TODO -->

## Bootique Linkmove

<!-- TODO -->

## Bootique Tapestry

<!-- TODO -->

## Bootique Jooq

<!-- TODO -->

## Bootique Liquibase

<!-- TODO -->

## Bootique Flyway

<!-- TODO -->

## Bootique Job

<!-- TODO -->

## Bootique Kafka Client

<!-- TODO -->

## Bootique Jcache

<!-- TODO -->

## Bootique Curator

<!-- TODO -->

## Bootique Metrics

<!-- TODO -->

## Bootique Rabbitmq Client

<!-- TODO -->

## WIP

* We still adding DSL for existing modules
* There are lack of tests
* Kotlin Script Startup time can be optimized
