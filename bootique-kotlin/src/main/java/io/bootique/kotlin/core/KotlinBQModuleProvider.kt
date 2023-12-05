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

import io.bootique.BQModuleProvider
import io.bootique.di.BQModule
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Helper class to use APIs native to Kotlin in Bootique DI.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinBQModuleProvider : BQModuleProvider {
    val module: BQModule

    override fun module(): BQModule {
        return module
    }

    val configs: Map<String, Type>
        get() = mapOf()

    override fun configs(): Map<String, Type> {
        return configs
    }

    /**
     * Property for more native to Kotlin usage.
     */
    val name: String
        get() = ""

    override fun name(): String {
        return name
    }

    /**
     * Provide property instead of function to use [KClass] instead of [Class].
     */
    val overrides: Collection<KClass<out BQModule>>
        get() = listOf()

    override fun overrides(): Collection<Class<out BQModule>> {
        return overrides.map { it.java }
    }

    /**
     * Property for more native to Kotlin usage.
     */
    val dependencies: Collection<BQModuleProvider>
        get() = listOf()

    override fun dependencies(): Collection<BQModuleProvider> {
        return dependencies
    }
}
