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

package io.bootique.kotlin.core

import io.bootique.*
import io.bootique.command.CommandOutcome
import io.bootique.di.Key
import io.bootique.log.BootLogger
import io.bootique.shutdown.ShutdownManager
import kotlin.reflect.KClass

/**
 * A Kotlin version of main launcher class of [Bootique].
 *
 * @deprecated No longer supported. Kotlin users should be able to use "regular" Java Bootique API
 */
@Deprecated(message = "deprecated since 3.0. Won't be a part of 4.0")
interface KotlinBootiqueInterface {
    /**
     * [Bootique.bootLogger]
     */
    fun bootLogger(bootLogger: BootLogger): KotlinBootiqueInterface

    /**
     * [Bootique.shutdownManager]
     */
    fun shutdownManager(shutdownManager: ShutdownManager): KotlinBootiqueInterface

    /**
     * [Bootique.args]
     */
    fun args(vararg args: String): KotlinBootiqueInterface

    /**
     * [Bootique.autoLoadModules]
     */
    fun autoLoadModules(): KotlinBootiqueInterface

    /**
     * [Bootique.override]
     */
    fun override(overriddenTypes: KClass<out BQModule>): KotlinBQModuleOverrideBuilder<KotlinBootiqueInterface>

    /**
     * [Bootique.module]
     */
    fun module(moduleType: KClass<out BQModule>): KotlinBootiqueInterface


    /**
     * [Bootique.modules]
     */
    fun modules(vararg moduleTypes: KClass<out BQModule>): KotlinBootiqueInterface

    /**
     * [Bootique.module]
     */
    fun module(module: BQModule): KotlinBootiqueInterface

    /**
     * [Bootique.modules]
     */
    fun modules(vararg modules: BQModule): KotlinBootiqueInterface

    /**
     * [Bootique.moduleProvider]
     */
    fun moduleProvider(moduleProvider: BQModuleProvider): KotlinBootiqueInterface

    /**
     * [Bootique.createRuntime]
     */
    fun createRuntime(): KotlinBQRuntime

    /**
     * [Bootique.exec]
     */
    fun exec(): CommandOutcome
}

/**
 * [KotlinBootiqueInterface]
 */
class KotlinBootique(
    args: Array<String>
) : KotlinBootiqueInterface {
    private val bootique = Bootique.app(*args)

    override fun bootLogger(bootLogger: BootLogger): KotlinBootiqueInterface {
        bootique.bootLogger(bootLogger)
        return this
    }

    override fun shutdownManager(shutdownManager: ShutdownManager): KotlinBootiqueInterface {
        bootique.shutdownManager(shutdownManager)
        return this
    }

    override fun args(vararg args: String): KotlinBootiqueInterface {
        bootique.args(*args)
        return this
    }

    override fun autoLoadModules(): KotlinBootiqueInterface {
        bootique.autoLoadModules()
        return this
    }

    override fun override(overriddenTypes: KClass<out BQModule>): KotlinBQModuleOverrideBuilder<KotlinBootiqueInterface> {
        return object : KotlinBQModuleOverrideBuilder<KotlinBootiqueInterface> {
            override fun with(module: BQModule): KotlinBootiqueInterface {
                bootique.override(overriddenTypes.java).with(module)
                return this@KotlinBootique
            }

            override fun with(moduleType: KClass<out BQModule>): KotlinBootiqueInterface {
                bootique.override(overriddenTypes.java).with(moduleType.java)
                return this@KotlinBootique
            }

            override fun with(moduleType: Class<out BQModule>): KotlinBootiqueInterface {
                bootique.override(overriddenTypes.java).with(moduleType)
                return this@KotlinBootique
            }
        }
    }

    override fun module(moduleType: KClass<out BQModule>): KotlinBootiqueInterface {
        bootique.module(moduleType.java)
        return this
    }

    override fun modules(vararg moduleTypes: KClass<out BQModule>): KotlinBootiqueInterface {
        moduleTypes.forEach { bootique.module(it.java) }
        return this
    }

    override fun module(module: BQModule): KotlinBootiqueInterface {
        bootique.module(module)
        return this
    }

    override fun modules(vararg modules: BQModule): KotlinBootiqueInterface {
        modules.forEach { bootique.module(it) }
        return this
    }

    override fun moduleProvider(moduleProvider: BQModuleProvider): KotlinBootiqueInterface {
        bootique.moduleProvider(moduleProvider)
        return this
    }

    override fun createRuntime(): KotlinBQRuntime {
        return DefaultKotlinBQRuntime(bootique.createRuntime())
    }

    override fun exec(): CommandOutcome {
        return bootique.exec()
    }
}

interface KotlinBQModuleOverrideBuilder<T> : BQModuleOverrideBuilder<T> {

    /**
     * [BQModuleOverrideBuilder.with]
     */
    fun with(moduleType: KClass<out BQModule>): T
}

interface KotlinBQRuntime {

    /**
     * [BQRuntime.getBootLogger]
     */
    val bootLogger: BootLogger

    /**
     * [BQRuntime.getArgs]
     */
    val args: Array<String>

    /**
     * [BQRuntime.getInstance]
     */
    fun <T : Any> getInstance(type: KClass<T>): T

    /**
     * [BQRuntime.getInstance]
     */
    fun <T> getInstance(diKey: Key<T>): T

    /**
     * [BQRuntime.run]
     */
    fun run(): CommandOutcome

    /**
     * [BQRuntime.shutdown]
     */
    fun shutdown()
}

class DefaultKotlinBQRuntime(
    private val runtime: BQRuntime
) : KotlinBQRuntime {
    override val bootLogger: BootLogger
        get() = runtime.bootLogger

    override val args: Array<String>
        get() = runtime.args

    override fun <T : Any> getInstance(type: KClass<T>): T {
        return runtime.getInstance(type.java)
    }

    override fun <T> getInstance(diKey: Key<T>): T {
        return runtime.getInstance(diKey)
    }

    override fun run(): CommandOutcome {
        return runtime.run()
    }

    override fun shutdown() {
        return runtime.shutdown()
    }
}
