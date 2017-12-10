package io.bootique.kotlin.guice

import com.google.inject.Key
import com.google.inject.TypeLiteral

/**
 * Constructs a new [Key] using kotlin reified generics.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
inline fun <reified T> key() = object : Key<T>() {}

/**
 * Constructs a new [TypeLiteral] using kotlin reified generics.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
inline fun <reified T> typeLiteral() = object : TypeLiteral<T>() {}
