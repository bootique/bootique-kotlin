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

package io.bootique.kotlin.sample

import com.google.inject.Inject
import com.google.inject.Provider
import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.config.withKotlinConfig
import io.bootique.kotlin.core.KotlinBootique
import io.bootique.kotlin.extra.config
import io.bootique.kotlin.guice.KotlinBinder
import io.bootique.kotlin.guice.KotlinModule

fun main(args: Array<String>) {
    val instance = KotlinBootique(arrayOf("--config=classpath:config.kts", "--config=classpath:config1.kts", "--server"))
        .withKotlinConfig()
        .autoLoadModules()
        .module(ApplicationModule::class)
        .createRuntime()
        .getInstance(TestConfig::class)

    println(instance)
}

class ApplicationModule : KotlinModule {
    override fun configure(binder: KotlinBinder) {
        binder.bind(TestConfig::class).toProvider(TestConfigProvider::class)
    }
}

class TestConfigProvider @Inject constructor(
    private val configurationFactory: ConfigurationFactory
) : Provider<TestConfig> {
    override fun get(): TestConfig {
        return configurationFactory.config("test")
    }
}

data class TestConfig(
    val name: String,
    val products: List<String>
)
