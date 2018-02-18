package io.bootique.kotlin.sample

import io.bootique.jetty.JettyModule
import io.bootique.kotlin.core.KotlinBootique
import io.bootique.logback.LogbackModule

fun main(args: Array<String>) {
    KotlinBootique(args)
        .module(LogbackModule::class)
        .module(JettyModule::class)
        .module(KotlinConfigModule::class)
        .exec()
        .exit()
}
