package io.bootique.kotlin.sample

import io.bootique.BQCoreModule
import io.bootique.kotlin.bootique
import io.bootique.kotlin.module
import io.bootique.kotlin.modules

fun main(args: Array<String>) {
    bootique(args) {
        module<BQCoreModule>()
        module(BQCoreModule::class)
        modules(BQCoreModule::class, BQCoreModule::class)
        autoLoadModules()
    }
}