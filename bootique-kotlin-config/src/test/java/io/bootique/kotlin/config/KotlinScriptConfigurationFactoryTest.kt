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

package io.bootique.kotlin.config

import io.bootique.config.PolymorphicConfiguration
import io.bootique.kotlin.extra.config
import io.bootique.type.TypeRef
import org.junit.Assert.assertEquals
import org.junit.Test

class KotlinScriptConfigurationFactoryTest {
    @Test
    fun `test absence of configuration`() {
        val confSource = setOf("classpath:sample.bq.kts")
        val compiler = BQConfigurationScriptHostProvider().get()
        val configurationFactory = KotlinScriptConfigurationFactory(confSource, compiler)

        val config = configurationFactory.config<SampleConfiguration>("test")

        assertEquals("", config.a)
        assertEquals(1, config.b)
    }

    @Test
    fun `test configuration`() {
        val confSource = setOf("classpath:sample.bq.kts")
        val compiler = BQConfigurationScriptHostProvider().get()
        val configurationFactory = KotlinScriptConfigurationFactory(confSource, compiler)

        val config = configurationFactory.config<String>("sample")

        assertEquals("Allons-y!", config)
    }

    private data class SampleConfiguration(
        val a: String = "",
        val b: Int = 1
    )

    /**
     * The whole idea behind Kotlin Scripting Configuration:
     *
     * 1. Configuration Autocomplete in IDE
     * 2. Using code configuration no need for tricky PolymorphicConfiguration
     *
     * But KotlinScriptConfigurationFactory was not expect to handle case when
     * using PolymorphicConfiguration nothing provided as config.
     * In this case expected result is just empty map.
     */
    @Test
    fun `test empty polymorphic configuration`() {
        val confSource = setOf("classpath:sample.bq.kts")
        val compiler = BQConfigurationScriptHostProvider().get()
        val configurationFactory = KotlinScriptConfigurationFactory(confSource, compiler)

        val config = configurationFactory.config(object : TypeRef<Map<String, EmptyPC>>() {}, "jdbc")

        assertEquals(emptyMap<String, EmptyPC>(), config)
    }

    private class EmptyPC : PolymorphicConfiguration
}
