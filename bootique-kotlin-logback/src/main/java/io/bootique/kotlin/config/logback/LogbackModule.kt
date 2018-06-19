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

package io.bootique.kotlin.config.logback

import io.bootique.logback.LogbackContextFactory
import io.bootique.logback.LogbackLevel
import io.bootique.logback.LoggerFactory
import io.bootique.logback.appender.AppenderFactory
import io.bootique.logback.appender.ConsoleAppenderFactory
import io.bootique.logback.appender.ConsoleTarget
import io.bootique.logback.appender.FileAppenderFactory
import io.bootique.logback.policy.FixedWindowPolicyFactory
import io.bootique.logback.policy.RollingPolicyFactory
import io.bootique.logback.policy.SizeAndTimeBasedPolicyFactory
import io.bootique.logback.policy.TimeBasedPolicyFactory
import kotlin.reflect.KClass

/**
 * Configuration DSL for LogbackModule.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
fun logbackContextFactory(
    logFormat: String,
    level: LogbackLevel = LogbackLevel.info,
    loggers: Map<String, LoggerFactory> = mapOf(),
    appenders: List<AppenderFactory> = listOf(),
    useLogbackConfig: Boolean = false,
    debugLogback: Boolean = false
) = LogbackContextFactory().also {
    it.setLogFormat(logFormat)
    it.level = level
    it.loggers = loggers
    it.appenders = appenders
    it.setUseLogbackConfig(useLogbackConfig)
    it.setDebugLogback(debugLogback)
}

fun logger(
    name: String,
    level: LogbackLevel = LogbackLevel.info
): Pair<String, LoggerFactory> {
    return name to loggerFactory(level)
}

fun logger(
    klass: KClass<*>,
    level: LogbackLevel = LogbackLevel.info
): Pair<String, LoggerFactory> {
    return klass.qualifiedName!! to loggerFactory(level)
}

fun loggerFactory(
    level: LogbackLevel = LogbackLevel.info
) = LoggerFactory().also {
    it.setLevel(level);
}

fun fileAppender(
    logFormat: String,
    file: String,
    rollingPolicy: RollingPolicyFactory
) = FileAppenderFactory().also {
    it.logFormat = logFormat
    it.file = file
    it.rollingPolicy = rollingPolicy
}

fun consoleAppender(
    logFormat: String,
    target: ConsoleTarget = ConsoleTarget.stdout
) = ConsoleAppenderFactory().also {
    it.logFormat = logFormat
    it.target = target
}

fun timeBasedPolicy(
    fileNamePattern: String,
    totalSize: String,
    historySize: Int
) = TimeBasedPolicyFactory().also {
    it.setFileNamePattern(fileNamePattern)
    it.setTotalSize(totalSize)
    it.setHistorySize(historySize)
}

fun sizeTimeBasedPolicy(
    fileNamePattern: String,
    totalSize: String,
    fileSize: String,
    historySize: Int
) = SizeAndTimeBasedPolicyFactory().also {
    it.setFileNamePattern(fileNamePattern)
    it.setTotalSize(totalSize)
    it.setFileSize(fileSize)
    it.setHistorySize(historySize)
}

fun fixedWindowPolicy(
    fileNamePattern: String,
    fileSize: String,
    historySize: Int
) = FixedWindowPolicyFactory().also {
    it.setFileNamePattern(fileNamePattern)
    it.setFileSize(fileSize)
    it.setHistorySize(historySize)
}
