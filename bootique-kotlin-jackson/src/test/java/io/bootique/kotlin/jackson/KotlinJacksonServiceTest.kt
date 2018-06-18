/**
 *  Licensed to ObjectStyle LLC under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ObjectStyle LLC licenses
 *  this file to you under the Apache License, Version 2.0 (the
 *  “License”); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.bootique.kotlin.jackson

import com.google.inject.Provides
import com.google.inject.Singleton
import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.core.KotlinBootique
import io.bootique.kotlin.extra.config
import io.bootique.kotlin.guice.KotlinBinder
import io.bootique.kotlin.guice.KotlinModule
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class KotlinJacksonServiceTest {

    @Test
    fun test() {
        val config = KotlinBootique(arrayOf("--config=classpath:config.yml"))
            .module(TestKotlinModule::class)
            .module(KotlinJacksonServiceModuleProvider())
            .createRuntime()
            .getInstance(TestConfig::class)

        assertEquals("Foo Bar", config.name)
        assertEquals("Foo", config.names[0])
        assertEquals("Bar", config.names[1])
    }
}

class TestKotlinModule : KotlinModule {
    override fun configure(binder: KotlinBinder) {
    }

    @Provides
    @Singleton
    fun config(configurationFactory: ConfigurationFactory): TestConfig =
        configurationFactory.config("test")
}

data class TestConfig(
    val name: String,
    val names: List<String>
)
