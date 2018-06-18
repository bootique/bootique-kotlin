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

package io.bootique.kotlin.config.modules

import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.config.DefaultKotlinScriptCompiler
import io.bootique.kotlin.config.KotlinScriptCompiler
import io.bootique.kotlin.config.KotlinScriptConfigurationFactory
import io.bootique.kotlin.guice.KotlinBinder
import io.bootique.kotlin.guice.KotlinModule

/**
 * Module for overriding default JsonNodeConfigurationFactory.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
class KotlinConfigModule : KotlinModule {
    override fun configure(binder: KotlinBinder) {
        binder.bind(KotlinScriptCompiler::class).to(DefaultKotlinScriptCompiler::class).asSingleton()
        binder.bind(ConfigurationFactory::class).to(KotlinScriptConfigurationFactory::class).asSingleton()
    }
}
