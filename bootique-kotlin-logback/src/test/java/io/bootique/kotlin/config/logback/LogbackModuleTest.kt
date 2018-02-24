package io.bootique.kotlin.config.logback

import io.bootique.logback.LogbackLevel
import io.bootique.logback.appender.ConsoleTarget
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class LogbackModuleTest {

    @Test
    fun logbackSampleConfig() {
        val logFormat = "%-5p [%d{ISO8601,UTC}] %thread %c{20}: %m%n%rEx"
        val config = logbackContextFactory(
            logFormat = logFormat,
            useLogbackConfig = false,
            debugLogback = false,
            level = LogbackLevel.warn,
            loggers = mapOf(
                logger(LogbackModuleTest::class, LogbackLevel.error),
                logger("TestLogger", LogbackLevel.trace)
            ),
            appenders = listOf(
                consoleAppender(
                    logFormat = logFormat,
                    target = ConsoleTarget.stderr
                ),
                fileAppender(logFormat, "abc", timeBasedPolicy(
                    fileNamePattern = "Abc_%d",
                    totalSize = "2m",
                    historySize = 1
                ))
            )
        )

        assertEquals(LogbackLevel.warn, config.level)
        assertNotNull(config.loggers[LogbackModuleTest::class.qualifiedName])
        assertNotNull(config.loggers["TestLogger"])
        assertEquals(2, config.appenders.size)
    }
}
