package io.bootique.kotlin.config.logback

import io.bootique.kotlin.config.modules.BootiqueConfiguration
import io.bootique.kotlin.config.modules.FactoryDSL
import io.bootique.logback.LogbackContextFactory
import io.bootique.logback.LogbackLevel
import io.bootique.logback.LoggerFactory
import io.bootique.logback.appender.AppenderFactory
import io.bootique.logback.appender.ConsoleAppenderFactory
import io.bootique.logback.appender.FileAppenderFactory
import io.bootique.logback.policy.FixedWindowPolicyFactory
import io.bootique.logback.policy.SizeAndTimeBasedPolicyFactory
import io.bootique.logback.policy.TimeBasedPolicyFactory
import kotlin.reflect.KClass

/**
 * Configuration DSL for LogbackModule.
 * TODO. Describe and test.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
inline fun BootiqueConfiguration.logback(body: (@FactoryDSL LogbackContextFactory).() -> Unit) {
    this.addConfig("log" to LogbackContextFactory().also { factory ->
        // Since default impl immutable, make sure that new map is mutable
        factory.loggers = mutableMapOf()
        factory.appenders = mutableListOf()
        body(factory)
    })
}

fun (@FactoryDSL LogbackContextFactory).logger(`package`: String, level: LogbackLevel) {
    this.loggers.put(`package`, LoggerFactory().also {
        it.setLevel(level)
    })
}

var (@FactoryDSL LogbackContextFactory).debug: Boolean
    get() = false
    set(value) = this.setDebugLogback(value)

var (@FactoryDSL LogbackContextFactory).useLogbackConfig: Boolean
    get() = false
    set(value) = this.setUseLogbackConfig(value)

fun (@FactoryDSL LogbackContextFactory).logger(clazz: KClass<*>, level: LogbackLevel) {
    this.loggers.put(clazz.java.`package`.name, LoggerFactory().also {
        it.setLevel(level)
    })
}

fun (@FactoryDSL LogbackContextFactory).appender(appenderFactory: AppenderFactory) {
    this.appenders.add(appenderFactory)
}

fun (@FactoryDSL LogbackContextFactory).consoleAppender(body: (@FactoryDSL ConsoleAppenderFactory).() -> Unit) {
    ConsoleAppenderFactory().also { factory ->
        body(factory)
        this.appenders.add(factory)
    }
}

fun (@FactoryDSL LogbackContextFactory).fileAppender(body: (@FactoryDSL FileAppenderFactory).() -> Unit) {
    FileAppenderFactory().also { factory ->
        body(factory)
        this.appenders.add(factory)
    }
}

fun (@FactoryDSL FileAppenderFactory).timeBasedRollingPolicy(body: (@FactoryDSL TimeBasedPolicyFactory).() -> Unit) {
    TimeBasedPolicyFactory().also { factory ->
        body(factory)
        this.rollingPolicy = factory
    }
}

fun (@FactoryDSL FileAppenderFactory).fixedWindowRollingPolicy(body: (@FactoryDSL FixedWindowPolicyFactory).() -> Unit) {
    FixedWindowPolicyFactory().also { factory ->
        body(factory)
        this.rollingPolicy = factory
    }
}

fun (@FactoryDSL FileAppenderFactory).sizeAndTimeRollingPolicy(body: (@FactoryDSL SizeAndTimeBasedPolicyFactory).() ->
Unit) {
    SizeAndTimeBasedPolicyFactory().also { factory ->
        body(factory)
        this.rollingPolicy = factory
    }
}
