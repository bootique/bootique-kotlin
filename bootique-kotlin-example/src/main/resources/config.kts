
import io.bootique.kotlin.config.modules.FactoryDSL
import io.bootique.kotlin.config.modules.appender
import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.config.modules.consoleAppender
import io.bootique.kotlin.config.modules.debug
import io.bootique.kotlin.config.modules.fileAppender
import io.bootique.kotlin.config.modules.fixedWindowRollingPolicy
import io.bootique.kotlin.config.modules.logback
import io.bootique.kotlin.config.modules.logger
import io.bootique.kotlin.config.modules.sizeAndTimeRollingPolicy
import io.bootique.kotlin.config.modules.timeBasedRollingPolicy
import io.bootique.kotlin.config.modules.useLogbackConfig
import io.bootique.kotlin.sample.TestConfig
import io.bootique.logback.LogbackLevel
import io.bootique.logback.appender.ConsoleAppenderFactory
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
    logback {
        useLogbackConfig = false
        debug = true
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
}