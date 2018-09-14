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

package io.bootique.kotlin.sample

import com.google.inject.Provides
import com.google.inject.Singleton
import io.bootique.ConfigModule
import io.bootique.config.ConfigurationFactory
import io.bootique.jetty.JettyModuleProvider
import io.bootique.kotlin.config.modules.KotlinConfigModuleProvider
import io.bootique.kotlin.core.KotlinBQModuleProvider
import io.bootique.kotlin.extra.config
import io.bootique.logback.LogbackModuleProvider

data class AppConfiguration(
    val name: String
)

class KotlinSampleModuleProvider : KotlinBQModuleProvider {
    override val module = KotlinSampleModule()
    override val dependencies = listOf(
        JettyModuleProvider(),
        LogbackModuleProvider(),
        KotlinConfigModuleProvider()
    )
}

class KotlinSampleModule : ConfigModule() {

    @Singleton
    @Provides
    fun createAppConfiguration(configurationFactory: ConfigurationFactory): AppConfiguration {
        return configurationFactory.config(configPrefix)
    }
}

