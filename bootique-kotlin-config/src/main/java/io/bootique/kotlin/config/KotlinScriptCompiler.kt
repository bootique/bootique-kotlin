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

package io.bootique.kotlin.config

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngineFactory
import java.net.URL

/**
 * Read InputStream from URL and eval Kotlin script file via JSR223 Engine.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
interface KotlinScriptCompiler {
    fun <T> execute(url: URL): T
}

class DefaultKotlinScriptCompiler : KotlinScriptCompiler {
    private val factory = KotlinJsr223JvmLocalScriptEngineFactory()
    private val scriptEngine = factory.scriptEngine

    @Suppress("UNCHECKED_CAST")
    override fun <T> execute(url: URL): T {
        return scriptEngine.eval(url.openStream().reader()) as T
    }
}