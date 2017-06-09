package io.bootique.kotlin

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
import io.bootique.logback.LogbackContextFactory
import io.bootique.logback.LogbackLevel
import io.bootique.logback.appender.ConsoleAppenderFactory
import io.bootique.logback.appender.ConsoleTarget
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class LogbackModuleTest {

    @Test fun logbackSampleConfig() {
        val config = config {
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
        }

        val logback = (config["log"] as LogbackContextFactory)

        assertEquals(false, logback.useLogbackConfig)
        assertEquals(false, logback.debug)
        assertEquals(LogbackLevel.warn, logback.level)
        assertNotNull(logback.loggers[FactoryDSL::class.java.`package`.name])
        assertEquals(3, logback.appenders.size)
    }

    @Test fun logbackConfig() {
        val config = config {
            logback {

            }
        }

        assertNotNull(config["log"])
    }
}