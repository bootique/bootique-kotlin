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

import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Provider
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.StringScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

interface BQConfigurationScriptHost {
    fun eval(stream: InputStream): BQConfigurationScript
}

internal class DefaultBQConfigurationScriptHost(
    private val scriptingHost: BasicJvmScriptingHost
) : BQConfigurationScriptHost {
    override fun eval(stream: InputStream): BQConfigurationScript {
        val script = readSource(stream)
        val result = scriptingHost.evalWithTemplate<ScriptingBQConfigurationScript>(StringScriptSource(script))
        @Suppress("UNCHECKED_CAST")
        return result.valueOrThrow().returnValue.scriptInstance as ScriptingBQConfigurationScript
    }

    private fun readSource(stream: InputStream): String {
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length = 0
        while (stream.read(buffer).also { length = it } != -1) {
            result.write(buffer, 0, length)
        }
        return result.toString("UTF-8")
    }
}

internal class BQConfigurationScriptHostProvider : Provider<BQConfigurationScriptHost> {
    override fun get(): BQConfigurationScriptHost {
        // Set classpath explicitly, https://youtrack.jetbrains.com/issue/KT-41795
        System.setProperty("kotlin.script.classpath", System.getProperty("java.class.path"))
        return DefaultBQConfigurationScriptHost(BasicJvmScriptingHost())
    }
}
