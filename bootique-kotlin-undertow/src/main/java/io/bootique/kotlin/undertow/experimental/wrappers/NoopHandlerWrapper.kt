package io.bootique.kotlin.undertow.experimental.wrappers

import io.bootique.kotlin.undertow.experimental.CoroutinesHandler
import io.bootique.kotlin.undertow.experimental.CoroutinesHandlerWrapper

/**
 * Handler wrapper which do nothing.
 *
 * @author Ibragimov Ruslan
 * @since 0.26
 */
object NoopHandlerWrapper : CoroutinesHandlerWrapper {
    override fun wrap(handler: CoroutinesHandler) = handler
}
