/**
 *  Licensed to ObjectStyle LLC under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ObjectStyle LLC licenses
 *  this file to you under the Apache License, Version 2.0 (the
 *  “License”); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

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
