package io.bootique.kotlin.config.modules

/**
 * Marker for configuration Builders
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
@DslMarker
@Target(AnnotationTarget.TYPE)
annotation class FactoryDSL
