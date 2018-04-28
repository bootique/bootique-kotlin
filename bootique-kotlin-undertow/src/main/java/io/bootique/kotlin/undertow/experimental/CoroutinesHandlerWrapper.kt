package io.bootique.kotlin.undertow.experimental

/**
 * Coroutines aware [io.undertow.server.HandlerWrapper] interface.
 *
 * @author Ibragimov Ruslan
 * @since 0.26
 */
interface CoroutinesHandlerWrapper {
    fun wrap(handler: CoroutinesHandler): CoroutinesHandler
}
