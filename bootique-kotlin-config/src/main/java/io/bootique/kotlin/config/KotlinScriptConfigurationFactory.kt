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

import io.bootique.config.ConfigurationFactory
import io.bootique.config.ConfigurationSource
import io.bootique.type.TypeRef
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType
import javax.inject.Inject
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
    private val scriptHost: BQConfigurationScriptHost
) : ConfigurationFactory {
    private val configs by lazy {
        val configs = mutableMapOf<String, Any>()

        configurationSource.get().use { stream ->
            stream.forEach { url ->
                configs.putAll(scriptHost.eval(url).getAll())
            }
        }

        configs
    }

    override fun <T : Any> config(expectedType: Class<T>, prefix: String): T {
        val config = configs[prefix] ?: createInstanceUsingDefaultConstructor(expectedType)

        if (config::class.isSubclassOf(expectedType.kotlin)) {
            @Suppress("UNCHECKED_CAST")
            return config as T
        } else {
            throw IllegalStateException("Expected type ${expectedType::class.qualifiedName}, actual type ${config::class.qualifiedName} for prefix '$prefix'")
        }
    }

    override fun <T : Any> config(type: TypeRef<out T>, prefix: String): T {
        @Suppress("UNCHECKED_CAST")
        return config(type.type.getRawType() as Class<T>, prefix)
    }

    private fun <T : Any> createInstanceUsingDefaultConstructor(type: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return type.constructors
            .find { it.parameterCount == 0 }
            ?.newInstance() as T
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
