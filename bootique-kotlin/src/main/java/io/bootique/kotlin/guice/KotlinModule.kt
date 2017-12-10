package io.bootique.kotlin.guice

import com.google.inject.Binder
import com.google.inject.Key
import com.google.inject.Module
import com.google.inject.Scope
import com.google.inject.Singleton
import com.google.inject.TypeLiteral
import com.google.inject.binder.AnnotatedBindingBuilder
import com.google.inject.binder.AnnotatedConstantBindingBuilder
import com.google.inject.binder.ConstantBindingBuilder
import com.google.inject.binder.LinkedBindingBuilder
import com.google.inject.binder.ScopedBindingBuilder
import java.lang.reflect.Constructor
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinModule : Module {
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
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinBinder : Binder {
    fun <T : Any> bind(kclass: KClass<T>): KotlinAnnotatedBindingBuilder<T>
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class DefaultKotlinBinder(
    private val binder: Binder
) : Binder by binder, KotlinBinder {

    /**
     * Accepts [KClass] instead of [Class] as in [Binder].
     * Delegates actual implementation to [Binder].
     */
    override fun <T : Any> bind(kclass: KClass<T>): KotlinAnnotatedBindingBuilder<T> {
        return this.bind(kclass.java)
    }

    override fun <T : Any> bind(type: Class<T>): KotlinAnnotatedBindingBuilder<T> {
        return DefaultKotlinAnnotatedBindingBuilder(binder.bind(type))
    }

    override fun <T : Any> bind(key: Key<T>): KotlinLinkedBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(binder.bind(key))
    }

    override fun <T : Any> bind(typeLiteral: TypeLiteral<T>): KotlinAnnotatedBindingBuilder<T> {
        return DefaultKotlinAnnotatedBindingBuilder(binder.bind(typeLiteral))
    }

    override fun skipSources(vararg classesToSkip: Class<*>): KotlinBinder {
        return DefaultKotlinBinder(binder.skipSources(*classesToSkip))
    }

    override fun bindConstant(): KotlinAnnotatedConstantBindingBuilder {
        return DefaultKotlinAnnotatedConstantBindingBuilder(binder.bindConstant())
    }

    override fun withSource(source: Any): KotlinBinder {
        return DefaultKotlinBinder(binder.withSource(source))
    }
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinAnnotatedBindingBuilder<T : Any> : AnnotatedBindingBuilder<T>, KotlinLinkedBindingBuilder<T> {

    /**
     * Accepts [KClass] instead of [Class] as in [AnnotatedBindingBuilder].
     * Delegates actual implementation to [AnnotatedBindingBuilder].
     */
    fun annotatedWith(annotationType: KClass<out Annotation>): KotlinLinkedBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(this.annotatedWith(annotationType.java))
    }
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinLinkedBindingBuilder<T : Any> : LinkedBindingBuilder<T>, KotlinScopedBindingBuilder {

    /**
     * Accepts [KClass] instead of [Class] as in [LinkedBindingBuilder].
     * Delegates actual implementation to [LinkedBindingBuilder].
     */
    fun to(implementation: KClass<out T>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(this.to(implementation.java))
    }

    /**
     * Accepts [KClass] instead of [Class] as in [LinkedBindingBuilder].
     * Delegates actual implementation to [LinkedBindingBuilder].
     */
    fun toProvider(providerType: KClass<out Provider<out T>>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(this.toProvider(providerType.java))
    }
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinScopedBindingBuilder : ScopedBindingBuilder {

    /**
     * Renames [in] method to [inScope], since `in` in Kotlin is keyword.
     */
    fun inScope(scopeAnnotation: KClass<out Annotation>) {
        this.`in`(scopeAnnotation.java)
    }

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
        this.inScope(Singleton::class)
    }
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class DefaultKotlinAnnotatedBindingBuilder<T : Any>(
    private val builder: AnnotatedBindingBuilder<T>
) : AnnotatedBindingBuilder<T> by builder, KotlinAnnotatedBindingBuilder<T> {

    override fun annotatedWith(annotationType: Class<out Annotation>): KotlinLinkedBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(builder.annotatedWith(annotationType))
    }

    override fun annotatedWith(annotation: Annotation): KotlinLinkedBindingBuilder<T> {
        return DefaultKotlinLinkedBindingBuilder(builder.annotatedWith(annotation))
    }
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class DefaultKotlinLinkedBindingBuilder<T : Any>(
    private val builder: LinkedBindingBuilder<T>
) : LinkedBindingBuilder<T> by builder, KotlinLinkedBindingBuilder<T> {

    override fun to(targetKey: Key<out T>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.to(targetKey))
    }

    override fun to(implementation: TypeLiteral<out T>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.to(implementation))
    }

    override fun to(implementation: Class<out T>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.to(implementation))
    }

    override fun toProvider(providerType: Class<out Provider<out T>>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toProvider(providerType))
    }

    override fun toProvider(provider: com.google.inject.Provider<out T>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toProvider(provider))
    }

    override fun toProvider(providerKey: Key<out Provider<out T>>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toProvider(providerKey))
    }

    override fun toProvider(provider: Provider<out T>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toProvider(provider))
    }

    override fun toProvider(providerType: TypeLiteral<out Provider<out T>>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toProvider(providerType))
    }

    override fun <S : T> toConstructor(constructor: Constructor<S>, type: TypeLiteral<out S>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toConstructor(constructor, type))
    }

    override fun <S : T> toConstructor(constructor: Constructor<S>): KotlinScopedBindingBuilder {
        return DefaultKotlinScopedBindingBuilder(builder.toConstructor(constructor))
    }
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class DefaultKotlinScopedBindingBuilder(
    private val builder: ScopedBindingBuilder
) : ScopedBindingBuilder by builder, KotlinScopedBindingBuilder

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinAnnotatedConstantBindingBuilder : AnnotatedConstantBindingBuilder {
    fun annotatedWith(annotationType: KClass<out Annotation>): KotlinConstantBindingBuilder
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class DefaultKotlinAnnotatedConstantBindingBuilder(
    private val builder: AnnotatedConstantBindingBuilder
) : AnnotatedConstantBindingBuilder by builder, KotlinAnnotatedConstantBindingBuilder {

    /**
     * Accepts [KClass] instead of [Class] as in [AnnotatedConstantBindingBuilder].
     * Delegates actual implementation to [AnnotatedConstantBindingBuilder].
     */
    override fun annotatedWith(annotationType: KClass<out Annotation>): KotlinConstantBindingBuilder {
        return this.annotatedWith(annotationType.java)
    }

    override fun annotatedWith(annotation: Annotation): KotlinConstantBindingBuilder {
        return DefaultKotlinConstantBindingBuilder(builder.annotatedWith(annotation))
    }

    override fun annotatedWith(annotationType: Class<out Annotation>): KotlinConstantBindingBuilder {
        return DefaultKotlinConstantBindingBuilder(builder.annotatedWith(annotationType))
    }
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
interface KotlinConstantBindingBuilder : ConstantBindingBuilder {
    fun to(value: KClass<*>)
}

/**
 * Helper class to use APIs native to Kotlin in Guice.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class DefaultKotlinConstantBindingBuilder(
    private val builder: ConstantBindingBuilder
) : ConstantBindingBuilder by builder, KotlinConstantBindingBuilder {
    /**
     * Accepts [KClass] instead of [Class] as in [ConstantBindingBuilder].
     * Delegates actual implementation to [ConstantBindingBuilder].
     */
    override fun to(value: KClass<*>) {
        builder.to(value.java)
    }
}