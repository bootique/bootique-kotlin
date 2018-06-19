/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
