# Kotlin Extensions for Bootique and Bootique Modules

TL;DR;
* Use `KotlinBootique` instead of `Bootique`;
* Use `KotlinModule` instead of `Module`, you can use `KotlinModule` with `ConfigModule` (just inherit both);
* Use `KotlinBQModuleProvider` instead of `BQModuleProvider`;
* Use extensions defined in [Extensions.kt](https://github.com/bootique/bootique-kotlin/blob/master/bootique-kotlin/src/main/java/io/bootique/kotlin/extra/Extensions.kt);
* Use `bootique-kotlin-configuration` module to benefit from configuration written in Kotlin.

## Bootique

### Replacement for Bootique

`bootique-kotlin` provides replacement for `Bootique` class - `KotlinBootique`:

```kotlin
fun main(args: Array<String>) {
    KotlinBootique(arrayOf("--server"))
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

