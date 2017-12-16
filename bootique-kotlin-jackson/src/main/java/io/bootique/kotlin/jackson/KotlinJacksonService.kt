package io.bootique.kotlin.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.Inject
import io.bootique.config.PolymorphicConfiguration
import io.bootique.config.TypesFactory
import io.bootique.jackson.DefaultJacksonService
import io.bootique.jackson.JacksonService

/**
 * Returns [JacksonService] with [com.fasterxml.jackson.module.kotlin.KotlinModule] enabled.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
class KotlinJacksonService @Inject constructor(
    typesFactory: TypesFactory<PolymorphicConfiguration>
) : JacksonService {
    private val defaultJacksonService = DefaultJacksonService(typesFactory.types)

    override fun newObjectMapper(): ObjectMapper {
        return defaultJacksonService.newObjectMapper().also { mapper ->
            mapper.registerKotlinModule()
        }
    }
}
