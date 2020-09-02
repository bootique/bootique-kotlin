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

import io.bootique.config.ConfigurationSource
import io.bootique.kotlin.extra.config
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.URLClassLoader

class KotlinScriptConfigurationFactoryTest {
    @Test
    fun `test absence of configuration`() {
        val confSource = ConfigurationSource {
            listOf(URLClassLoader.getSystemResource("sample.bq.kts")).stream()
        }
        val compiler = BQConfigurationScriptHostProvider().get()
        val configurationFactory = KotlinScriptConfigurationFactory(confSource, compiler)

        val config = configurationFactory.config<SampleConfiguration>("test")

        assertEquals("", config.a)
        assertEquals(1, config.b)
    }

    @Test
    fun `test configuration`() {
        val confSource = ConfigurationSource {
            listOf(URLClassLoader.getSystemResource("sample.bq.kts")).stream()
        }
        val compiler = BQConfigurationScriptHostProvider().get()
        val configurationFactory = KotlinScriptConfigurationFactory(confSource, compiler)

        val config = configurationFactory.config<String>("sample")

        assertEquals("Allons-y!", config)
    }

    private data class SampleConfiguration(
        val a: String = "",
        val b: Int = 1
    )
}
