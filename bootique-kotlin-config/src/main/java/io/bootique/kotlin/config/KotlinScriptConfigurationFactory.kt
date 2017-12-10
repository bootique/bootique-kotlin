package io.bootique.kotlin.config

import com.google.inject.Inject
import io.bootique.config.ConfigurationFactory
import io.bootique.config.ConfigurationSource
import io.bootique.type.TypeRef


/**
 * Implementation of Bootique's ConfigurationFactory,
 * which used .kts files instead of .yaml.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
class KotlinScriptConfigurationFactory @Inject constructor(
    private val configurationSource: ConfigurationSource,
    private val kotlinScriptCompiler: KotlinScriptCompiler
): ConfigurationFactory {
    private val configs by lazy {
        val configs = mutableMapOf<String, Any>()

        configurationSource.get().use { stream ->
            stream.forEach { url ->
                configs.putAll(kotlinScriptCompiler.execute<Map<String, Any>>(url))
            }
        }

        configs
    }

    override fun <T : Any> config(expectedType: Class<T>, prefix: String): T {
        val type = configs[prefix] ?: throw RuntimeException("Config for prefix '$prefix' not found.")

        if (type::class.java.isAssignableFrom(expectedType)) {
            @Suppress("UNCHECKED_CAST")
            return type as T
        } else {
            throw RuntimeException("Expected type ${expectedType::class.qualifiedName}, actual type ${type::class.qualifiedName} for prefix '$prefix'")
        }
    }

    override fun <T : Any> config(type: TypeRef<out T>, prefix: String): T {
        TODO("""
            | Not Yet Implemented.
            | Please, fail an issues https://github.com/bootique/bootique-kotlin if you see this message.
        """.trimMargin())
    }
}



