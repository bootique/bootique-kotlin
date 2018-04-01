package io.bootique.kotlin.config.undertow

import io.bootique.undertow.UndertowFactory
import io.bootique.undertow.config.HttpListener
import io.bootique.undertow.config.HttpsListener

/**
 * Configuration DSL for UndertowModule.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
fun undertowFactory(
    httpListeners: List<HttpListener> = listOf(),
    httpsListeners: List<HttpsListener> = listOf(),
    bufferSize: Int? = null,
    ioThreads: Int? = null,
    workerThreads: Int? = null,
    directBuffers: Boolean? = null
) = UndertowFactory().also {
    it.httpListeners = httpListeners
    it.httpsListeners = httpsListeners
    it.bufferSize = bufferSize
    it.ioThreads = ioThreads
    it.workerThreads = workerThreads
    it.directBuffers = directBuffers
}

fun httpListener(
    port: Int,
    host: String = "0.0.0.0"
) = HttpListener().also {
    it.port = port
    it.host = host
}
