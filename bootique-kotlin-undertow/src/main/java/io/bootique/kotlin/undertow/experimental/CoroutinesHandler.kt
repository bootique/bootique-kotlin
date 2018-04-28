package io.bootique.kotlin.undertow.experimental

import io.undertow.server.HttpServerExchange

/**
 * Coroutines aware [io.undertow.server.HttpHandler] interface.
 *
 * @author Ibragimov Ruslan
 * @since 0.26
 */
interface CoroutinesHandler {
    suspend fun handleRequest(exchange: HttpServerExchange)
}
