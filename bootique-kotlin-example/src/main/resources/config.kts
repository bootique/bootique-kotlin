
import io.bootique.kotlin.config.logback.consoleAppender
import io.bootique.kotlin.config.logback.logbackContextFactory
import io.bootique.kotlin.config.logback.logger
import io.bootique.kotlin.config.modules.FactoryDSL
import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.sample.TestConfig
import io.bootique.logback.LogbackLevel
import io.bootique.logback.appender.ConsoleTarget

config {
    addConfig("test" to TestConfig(
        name = "Ruslan",
        products = listOf(
            "Ubuntu",
            "DELL",
            "OnePlus"
        )
    ))
    "log" to logbackContextFactory(
        useLogbackConfig = false,
        debugLogback = false,
        level = LogbackLevel.warn,
        loggers = mapOf(
            logger(FactoryDSL::class, LogbackLevel.error)
        ),
        appenders = listOf(
            consoleAppender(
                target = ConsoleTarget.stderr,
                logFormat = "[%d{dd/MMM/yyyy:HH:mm:ss}] %t %-5p %c{1}: %m%n"
            )
        ),
        logFormat = "[%d{dd/MMM/yyyy:HH:mm:ss}] %t %-5p %c{1}: %m%n"
    )
}
