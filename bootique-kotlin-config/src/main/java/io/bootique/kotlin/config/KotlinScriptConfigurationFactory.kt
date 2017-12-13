package io.bootique.kotlin.config

import com.google.inject.Inject
import io.bootique.config.ConfigurationFactory
import io.bootique.config.ConfigurationSource
import io.bootique.type.TypeRef
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType
import kotlin.reflect.full.isSubclassOf


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
) : ConfigurationFactory {
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
        val config = configs[prefix] ?: throw RuntimeException("Config for prefix '$prefix' not found.")

        if (config::class.isSubclassOf(expectedType.kotlin)) {
            @Suppress("UNCHECKED_CAST")
            return config as T
        } else {
            throw IllegalStateException("Expected type ${expectedType::class.qualifiedName}, actual type ${config::class.qualifiedName} for prefix '$prefix'")
        }
    }

    override fun <T : Any> config(type: TypeRef<out T>, prefix: String): T {
        return config(type.type.getRawType() as Class<T>, prefix)
    }
}

/**
 * Get raw type from [Type].
 */
fun Type.getRawType(): Class<*> {
    return when (this) {
        is Class<*> -> this
        is ParameterizedType -> this.rawType.getRawType()
        is GenericArrayType -> TODO("GenericArrayType not implemented")
        is WildcardType -> TODO("WildcardType not implemented")
        is TypeVariable<*> -> TODO("TypeVariable not implemented")
        else -> throw IllegalStateException("Not a classifier type (${this::class.java}): $this")
    }
}
