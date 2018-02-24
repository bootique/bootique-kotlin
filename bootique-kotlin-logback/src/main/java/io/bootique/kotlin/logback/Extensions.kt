package io.bootique.kotlin.logback

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Extension function for creating new logger instance.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
inline fun <reified T : Any> logger(): Logger = LoggerFactory.getLogger(T::class.java)
