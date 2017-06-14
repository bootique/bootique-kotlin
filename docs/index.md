# Kotlin Extensions for Bootique and Bootique Modules

## Bootique

See [Extensions.kt](https://github.com/bootique/bootique-kotlin/blob/master/bootique-kotlin/src/main/java/io/bootique/kotlin/Extensions.kt) for sources.

Extension: `ConfigurationFactory.config`

```kotlin
// Using Java Api
configurationFactory.config(SampleFactory::class.java, "sample")

// With Extension
configurationFactory.config<SampleFactory>("sample")

// Type Inference
@Singleton
@Provides
fun createAppConfiguration(configurationFactory: ConfigurationFactory): SampleFactory {
    return configurationFactory.config/* No Type Here */(configPrefix)
}
```

Extension: `LinkedBindingBuilder.toClass`

```kotlin
binder.bind(CommandRunner::class).toClass(CommandRunnerImpl::class)
```

Extension: `Bootique.module`, `Bootique.modules`

```kotlin
Bootique
    .app(*args)
    .module<LogbackModule>()
    .module(LogbackModule::class)
    .modules(LogbackModule::class, JettyModule::class)
    .autoLoadModules()
```

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

