package io.bootique.kotlin.sample

import io.bootique.Bootique
import io.bootique.jetty.JettyModule
import io.bootique.kotlin.module
import io.bootique.kotlin.modules
import io.bootique.logback.LogbackModule

fun main(args: Array<String>) {
    Bootique
        .app(*args)
        .module<LogbackModule>()
        .module(LogbackModule::class)
        .modules(LogbackModule::class, JettyModule::class)
        .autoLoadModules()
}