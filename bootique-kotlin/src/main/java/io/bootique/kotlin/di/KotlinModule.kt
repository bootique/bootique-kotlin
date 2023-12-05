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

package io.bootique.kotlin.di;

import io.bootique.BQModule
import io.bootique.di.*
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * @since 2.0
 * @deprecated No longer supported. Kotlin users should be able to use "regular" Java Bootique API
 */
@Deprecated(message = "deprecated since 3.0. Won't be a part of 4.0")
interface KotlinModule : BQModule {
    /**
     * Use KotlinModule#configure(KotlinBinder)
     */
    override fun configure(binder: Binder) {
        configure(DefaultKotlinBinder(binder))
    }

    /**
     * Override this method to use APIs native to Kotlin.
     */
    fun configure(binder: KotlinBinder)
}

/**
 * [KotlinBinder] adds own set of method which accepts or returns classes with APIs native to Kotlin.
 *
 * @since 2.0
 */
interface KotlinBinder : Binder {
    fun <T : Any> bind(kclass: KClass<T>): KotlinBindingBuilder<T>
}

/**
 * Helper class to use APIs native to Kotlin in Bootique DI.
 *
 * @since 2.0
 */
class DefaultKotlinBinder(
    private val binder: Binder
) : Binder by binder, KotlinBinder {

    /**
     * Accepts [KClass] instead of [Class] as in [Binder].
     * Delegates actual implementation to [Binder].
     */
    override fun <T : Any> bind(kclass: KClass<T>): KotlinBindingBuilder<T> {
        return this.bind(kclass.java)
    }

    override fun <T : Any> bind(type: Class<T>): KotlinBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(binder.bind(type))
    }

    override fun <T : Any> bind(type: Class<T>, name: String): KotlinBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(binder.bind(type, name))
    }

    override fun <T : Any> bind(type: Class<T>, annotationType: Class<out Annotation>): KotlinBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(binder.bind(type, annotationType))
    }

    override fun <T : Any> bind(key: Key<T>): KotlinBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(binder.bind(key))
    }
}

/**
 * Helper class to use APIs native to Kotlin in Bootique DI.
 *
 * @since 2.0
 */
interface KotlinBindingBuilder<T : Any> : BindingBuilder<T>, KotlinScopeBuilder {

    /**
     * Accepts [KClass] instead of [Class] as in [BindingBuilder].
     * Delegates actual implementation to [BindingBuilder].
     */
    fun to(implementation: KClass<out T>): KotlinScopeBuilder {
        return DefaultKotlinScopedBindingBuilder(this.to(implementation.java))
    }

    /**
     * Accepts [KClass] instead of [Class] as in [BindingBuilder].
     * Delegates actual implementation to [BindingBuilder].
     */
    fun toProvider(provider: KClass<out Provider<T>>): KotlinScopeBuilder {
        return DefaultKotlinScopedBindingBuilder(this.toProvider(provider.java))
    }

}

/**
 * Helper class to use APIs native to Kotlin in Bootique DI.
 *
 * @since 2.0
 */
interface KotlinScopeBuilder : ScopeBuilder {

    /**
     * Renames [in] method to [inScope], since `in` in Kotlin is keyword.
     */
    fun inScope(scope: Scope) {
        this.`in`(scope)
    }

    /**
     * Shortcut for common used `.in(Singleton::class)`.
     */
    fun asSingleton() {
        this.inSingletonScope()
    }
}

/**
 * Helper class to use APIs native to Kotlin in Bootique DI.
 *
 * @since 2.0
 */
class DefaultKotlinLinkedBindingBuilder<T : Any>(
    private val builder: BindingBuilder<T>
) : BindingBuilder<T> by builder, KotlinBindingBuilder<T> {

    override fun to(targetKey: Key<out T>): KotlinScopeBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.to(targetKey))
    }

    override fun to(implementation: Class<out T>): KotlinScopeBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.to(implementation))
    }

    override fun toProvider(providerType: Class<out Provider<out T>>): KotlinScopeBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toProvider(providerType))
    }

    override fun toProviderInstance(provider: Provider<out T>): KotlinScopeBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toProviderInstance(provider))
    }

}

/**
 * Helper class to use APIs native to Kotlin in Bootique DI.
 *
 * @since 2.0
 */
class DefaultKotlinScopedBindingBuilder(
    private val builder: ScopeBuilder
) : ScopeBuilder by builder, KotlinScopeBuilder
