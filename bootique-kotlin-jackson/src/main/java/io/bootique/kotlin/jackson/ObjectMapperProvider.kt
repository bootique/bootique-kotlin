package io.bootique.kotlin.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.Provider

/**
 * Register Kotlin module in default [ObjectMapper].
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class ObjectMapperProvider : Provider<ObjectMapper> {
    override fun get(): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
    }
}
