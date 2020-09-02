package io.bootique.kotlin.config

import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    displayName = "Bootique scripting configuration",
    fileExtension = "bq.kts",
    compilationConfiguration = BQConfigurationScriptConfiguration::class
)
abstract class ScriptingBQConfigurationScript : BQConfigurationScript {
    private val configs = mutableMapOf<String, Any>()

    /**
     * This method is used inside configuration files
     */
    @Suppress("unused")
    fun addConfig(key: String, config: Any) {
        configs[key] = config
    }

    @Suppress("UNCHECKED_CAST")
    override operator fun <T> get(key: String): T {
        return configs[key] as T
    }

    override fun getAll(): Map<String, Any> {
        return configs.toMap()
    }
}

interface BQConfigurationScript {
    operator fun <T> get(key: String): T?
    fun getAll(): Map<String, Any>
}

internal object BQConfigurationScriptConfiguration : ScriptCompilationConfiguration({
    jvm {
        dependenciesFromClassContext(
            ScriptingBQConfigurationScript::class,
            wholeClasspath = true
        )
    }

    ide {
        acceptedLocations(ScriptAcceptedLocation.Project)
    }
})
