package io.bootique.kotlin.config

import java.net.URL
import javax.inject.Provider
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.UrlScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

interface BQConfigurationScriptHost {
    fun eval(url: URL): BQConfigurationScript
}

internal class DefaultBQConfigurationScriptHost(
    private val scriptingHost: BasicJvmScriptingHost
) : BQConfigurationScriptHost {
    override fun eval(url: URL): BQConfigurationScript {
        val result = scriptingHost.evalWithTemplate<ScriptingBQConfigurationScript>(UrlScriptSource(url))
        @Suppress("UNCHECKED_CAST")
        return result.valueOrThrow().returnValue.scriptInstance as ScriptingBQConfigurationScript
    }
}

internal class BQConfigurationScriptHostProvider : Provider<BQConfigurationScriptHost> {
    override fun get(): BQConfigurationScriptHost {
        // Set classpath explicitly, https://youtrack.jetbrains.com/issue/KT-41795
        System.setProperty("kotlin.script.classpath", System.getProperty("java.class.path"))
        return DefaultBQConfigurationScriptHost(BasicJvmScriptingHost())
    }
}
